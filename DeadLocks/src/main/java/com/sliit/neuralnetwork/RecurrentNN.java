/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.neuralnetwork;

import com.sliit.exception.NeuralException;
import com.sliit.normalize.NormalizeDataset;
import com.sliit.rules.RuleContainer;
import org.apache.commons.io.FilenameUtils;
import org.datavec.api.records.reader.SequenceRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVSequenceRecordReader;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;
import weka.filters.Filter;
import weka.filters.supervised.instance.StratifiedRemoveFolds;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Heshani
 */
public class RecurrentNN {
    
    public int outputNum = 4;
    private int iterations = 5;
    private int seed = 1234;
    private MultiLayerNetwork model = null;
    public int HIDDEN_LAYER_COUNT = 2;
    public int numHiddenNodes = 5;
    public int inputs = 10;
    private String uploadDirectory = "D:/deadlocks/NN_data";
    private ArrayList<Map<String,Double>> roc;

    public RecurrentNN(){


    }

    public void buildModel(){

        System.out.println("Build model....");
        iterations = outputNum + 1;
        NeuralNetConfiguration.Builder builder = new NeuralNetConfiguration.Builder();
        builder.iterations(iterations);
        builder.learningRate(0.001);
        // builder.momentum(0.01);
        builder.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT);
        builder.seed(seed);
        builder.biasInit(1);
        builder.regularization(true).l2(1e-5);
        builder.updater(Updater.RMSPROP);
        builder.weightInit(WeightInit.XAVIER);

        NeuralNetConfiguration.ListBuilder list = builder.list();

        for(int i=0;i<HIDDEN_LAYER_COUNT;i++){

            GravesLSTM.Builder hiddenLayerBuilder = new GravesLSTM.Builder();
            hiddenLayerBuilder.nIn(i==0 ? inputs : numHiddenNodes);
            hiddenLayerBuilder.nOut(numHiddenNodes);
            hiddenLayerBuilder.activation("tanh");
            list.layer(i,hiddenLayerBuilder.build());
        }

        RnnOutputLayer.Builder outputLayer = new RnnOutputLayer.Builder(LossFunction.MCXENT);
        outputLayer.activation("softmax");
        outputLayer.nIn(numHiddenNodes);
        outputLayer.nOut(outputNum);
        list.layer(HIDDEN_LAYER_COUNT,outputLayer.build());
        list.pretrain(false);
        list.backprop(true);
        MultiLayerConfiguration configuration = list.build();
        model = new MultiLayerNetwork(configuration);
        model.init();
        //model.setListeners(new ScoreIterationListener(1));

    }

    public String trainModel(String modelName,String filePath,int outputs,int inputsTot) throws NeuralException {
        System.out.println("calling trainModel");
        try {

            System.out.println("Neural Network Training start");
            loadSaveNN(modelName,false);
            if(model== null){

                buildModel();
            }
            
            File fileGeneral = new File(filePath);
            CSVLoader loader = new CSVLoader();
            loader.setSource(fileGeneral);
            Instances instances = loader.getDataSet();
            instances.setClassIndex(instances.numAttributes()-1);
            StratifiedRemoveFolds stratified = new StratifiedRemoveFolds();
            String[] options = new String[6];
            options[0] = "-N";
            options[1] = Integer.toString(5);
            options[2] = "-F";
            options[3] = Integer.toString(1);
            options[4] = "-S";
            options[5] = Integer.toString(1);
            stratified.setOptions(options);
            stratified.setInputFormat(instances);
            stratified.setInvertSelection(false);
            Instances testInstances = Filter.useFilter(instances,stratified);
            stratified.setInvertSelection(true);
            Instances trainInstances = Filter.useFilter(instances,stratified);
            String directory = fileGeneral.getParent();
            CSVSaver saver = new CSVSaver();
            File trainFile = new File(directory+"/"+"normtrainadded.csv");
            File testFile = new File(directory+"/"+"normtestadded.csv");
            if(trainFile.exists()){

                trainFile.delete();
            }
            trainFile.createNewFile();
            if(testFile.exists()){

                testFile.delete();
            }
            testFile.createNewFile();
            saver.setFile(trainFile);
            saver.setInstances(trainInstances);
            saver.writeBatch();
            saver = new CSVSaver();
            saver.setFile(testFile);
            saver.setInstances(testInstances);
            saver.writeBatch();
            SequenceRecordReader recordReader = new CSVSequenceRecordReader(0,",");
            recordReader.initialize(new org.datavec.api.split.FileSplit(trainFile));
            SequenceRecordReader testReader = new CSVSequenceRecordReader(0,",");
            testReader.initialize(new org.datavec.api.split.FileSplit(testFile));
            DataSetIterator iterator = new org.deeplearning4j.datasets.datavec.SequenceRecordReaderDataSetIterator(recordReader,2,outputs,inputsTot,false);
            DataSetIterator testIterator = new org.deeplearning4j.datasets.datavec.SequenceRecordReaderDataSetIterator(testReader,2,outputs,inputsTot,false);
            roc = new ArrayList<Map<String, Double>>();
            String statMsg="";
            Evaluation evaluation;

            for(int i=0;i<100;i++) {
                if(i%2==0) {

                    model.fit(iterator);
                    evaluation = model.evaluate(testIterator);
                }else{

                    model.fit(testIterator);
                    evaluation = model.evaluate(iterator);
                }
                Map<String, Double> map = new HashMap<String, Double>();
                Map<Integer,Integer> falsePositives = evaluation.falsePositives();
                Map<Integer,Integer> trueNegatives = evaluation.trueNegatives();
                Map<Integer,Integer> truePositives = evaluation.truePositives();
                Map<Integer,Integer> falseNegatives = evaluation.falseNegatives();
                double fpr = falsePositives.get(1)/(falsePositives.get(1)+trueNegatives.get(1));
                double tpr = truePositives.get(1)/(truePositives.get(1)+falseNegatives.get(1));
                map.put("FPR", fpr);
                map.put("TPR", tpr);
                roc.add(map);
                statMsg = evaluation.stats();
                iterator.reset();
                testIterator.reset();
            }
            loadSaveNN(modelName,true);
            System.out.println("ROC "+roc);
            
            return statMsg;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error ocuured while building neural netowrk :"+e.getMessage());
            throw new NeuralException(e.getLocalizedMessage(),e);
        }
    }

    public boolean generateModel(String modelName){
        System.out.println("recNN generateModel");

        boolean status = false;
        try {

            loadSaveNN(modelName, true);
            status = true;
        }catch (Exception e){

            System.out.println("Error occurred:"+e.getLocalizedMessage());
        }
        return status;
    }

    private void loadSaveNN(String name,boolean save){
        System.out.println("recNN loadSaveNN");

        File directory = new File(uploadDirectory);
        File[] allNN = directory.listFiles();
        boolean status = false;
        try {

            if(model == null && save){

                buildModel();
            }
            if(allNN != null && allNN.length > 0) {
                for (File NN : allNN) {

                    String fnme = FilenameUtils.removeExtension(NN.getName());
                    if (name.equals(fnme)) {

                        status = true;
                        if (save) {

                            ModelSerializer.writeModel(model,NN,true);
                            System.out.println("Model Saved With Weights Successfully");

                        } else {

                            model = ModelSerializer.restoreMultiLayerNetwork(NN);
                        }
                        break;
                    }
                }
            }
            if(!status && save){

                //File tempFIle = File.createTempFile(name,".zip",directory);
                File tempFile = new File(directory.getAbsolutePath()+"/"+name+".zip");
                if(!tempFile.exists()){

                    tempFile.createNewFile();
                }
                ModelSerializer.writeModel(model,tempFile,true);
            }
        } catch (IOException e) {
            System.out.println("Error occurred:"+e.getMessage());
        }
    }

    public String testModel(String modelName,String[] rawData,Map<Integer,String> map,int inputs,int outputs,String ruleModelSavePath) throws Exception{
        System.out.println("calling testmodel");
        
        String status = "";
        String fpath = uploadDirectory;
        FileWriter fwriter = new FileWriter(uploadDirectory+"original/insertdata.csv",true);
        fwriter.write("\n");
        fwriter.write(rawData[0]);
        fwriter.close();
        if(model==null) {
            loadSaveNN(modelName, false);
        }
        NormalizeDataset norm = new NormalizeDataset(uploadDirectory + "original/insertdata.csv");
        norm.updateStringValues(map);
        norm.whiteningData();
        norm.normalizeDataset();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(uploadDirectory + "originalnorminsertdata.csv")));
        String output="";
        String prevOutput = "";
        while((output = bufferedReader.readLine())!=null){

            prevOutput = output;
        }
        bufferedReader.close();
        File readFile = new File(uploadDirectory+"normtest.csv");
        if(readFile.exists()){

            readFile.delete();
        }
        readFile.createNewFile();
        PrintWriter writer = new PrintWriter(readFile);
        writer.println(prevOutput);
        writer.flush();
        writer.close();
        SequenceRecordReader recordReader = new CSVSequenceRecordReader(0, ",");
        recordReader.initialize(new org.datavec.api.split.FileSplit(new File(uploadDirectory+"normtest.csv")));
        DataSetIterator iterator = new org.deeplearning4j.datasets.datavec.SequenceRecordReaderDataSetIterator(recordReader, 2, outputs, inputs, false);
        INDArray outputArr = null;
        while (iterator.hasNext()) {

            DataSet ds = iterator.next();
            INDArray provided = ds.getFeatureMatrix();
            outputArr = model.rnnTimeStep(provided);
        }
        //INDArray finalOutput = Nd4j.argMax(outputArr,1);
        double result = Double.parseDouble(Nd4j.argMax(outputArr, 1).toString());
        if (result == 0.0) {

            status = "Normal Transaction";
        } else {

            status = "Fraud Transaction, ";
            bufferedReader = new BufferedReader(new FileReader(new File(uploadDirectory+"original/insertdata.csv")));
            String heading = "";
            heading = bufferedReader.readLine();
            bufferedReader.close();
            File ruleFile = new File(uploadDirectory+"normrules.csv");
            if(ruleFile.exists()){

                ruleFile.delete();
            }
            ruleFile.createNewFile();
            PrintWriter writeNew = new PrintWriter(ruleFile);
            writeNew.println(heading);
            writeNew.println(rawData[0]);
            writeNew.flush();
            writeNew.close();
            RuleContainer engine = new RuleContainer(fpath + "original/insertdata.csv");
            engine.geneateModel(ruleModelSavePath, false);
            String finalStatus = status+"Attack Type:"+engine.predictionResult(uploadDirectory+"normrules.csv");
            status = finalStatus;
        }
        System.out.println("testModel status "+status);
        return status;
    }

    public void setUploadDirectory(String uploadDirectory) {

        this.uploadDirectory = uploadDirectory;

    }

    public static void main(String[] args) {

        RecurrentNN neural_network = new RecurrentNN();
        System.out.println("start=======================");
        try {
            neural_network.inputs = 10;
            neural_network.numHiddenNodes = 5;
            neural_network.HIDDEN_LAYER_COUNT = 2;
            neural_network.outputNum = 2;
            neural_network.buildModel();
            String output = neural_network.trainModel("nn","D:/deadlocks/NN_data/frauddetectkdddataset.csv",2,10);
            System.out.println("output "+output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Map<String, Double>> getRoc() {
        return roc;
    }
}
