/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.normalize;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;
import weka.core.converters.LibSVMSaver;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.RandomProjection;

import java.io.*;
import java.util.Map;

/**
 *
 * @author Heshani
 */
public class NormalizeDataset {
    
    private Log log = LogFactory.getLog(NormalizeDataset.class);
    File csvFile;
    CSVLoader csv;
    File tempFIle;
    File reducedDiemensionFile;
    File outputFile;

    public NormalizeDataset(String filePath){

        csvFile = new File(filePath);
        csv = new CSVLoader();
    }

    public String normalizeDataset(){
        System.out.println("start normalizing data");

        String filePathOut = "";
        try {

            CSVLoader loader = new CSVLoader();
            if(reducedDiemensionFile!=null){

                loader.setSource(reducedDiemensionFile);
            }else {
                if (tempFIle != null && tempFIle.exists()) {

                    loader.setSource(tempFIle);
                } else {

                    loader.setSource(csvFile);
                }
            }
            Instances dataInstance = loader.getDataSet();
            Normalize normalize = new Normalize();
            dataInstance.setClassIndex(dataInstance.numAttributes()-1);
            normalize.setInputFormat(dataInstance);
            String directory = csvFile.getParent();
            outputFile = new File(directory+"/"+"normalized"+csvFile.getName());
            if(!outputFile.exists()){

                outputFile.createNewFile();
            }
            CSVSaver saver = new CSVSaver();
            saver.setFile(outputFile);
            for(int i=1;i<dataInstance.numInstances();i++){

                normalize.input(dataInstance.instance(i));
            }
            normalize.batchFinished();
            Instances outPut = new Instances(dataInstance,0);
            for(int i=1;i<dataInstance.numInstances();i++) {

                outPut.add(normalize.output());
            }
            Attribute attribute = dataInstance.attribute(outPut.numAttributes()-1);
            for(int j=0;j<attribute.numValues();j++) {

                if(attribute.value(j).equals("normal.")) {
                    outPut.renameAttributeValue(attribute, attribute.value(j),"0");
                }else{
                    outPut.renameAttributeValue(attribute,attribute.value(j),"1");
                }
            }
            saver.setInstances(outPut);
            saver.writeBatch();
            writeToNewFile(directory);
            filePathOut = directory+"norm"+csvFile.getName();
            if(tempFIle != null) {

                tempFIle.delete();
            }
            if(reducedDiemensionFile != null) {

                reducedDiemensionFile.delete();
            }
            outputFile.delete();
        } catch (IOException e) {

            log.error("Error occurred:"+e.getMessage());
        } catch (Exception e) {

            log.error("Error occurred:"+e.getMessage());
        }
        return filePathOut;
    }

    public boolean updateStringValues(Map<Integer,String> values){
        System.out.println("updating String Values");

        boolean status = false;
        try {

            csv.setSource(csvFile);
            Instances dataInstance = csv.getDataSet();
            for(int i=0;i<dataInstance.numInstances();i++){

                if(values.containsKey(i)){

                    Attribute attribute = dataInstance.attribute(i);
                    for(int j=0;j<attribute.numValues();j++) {
                        dataInstance.renameAttributeValue(attribute, attribute.value(j), j + "");
                    }
                }
            }
            tempFIle = new File(csvFile.getParent()+"/temp.csv");
            CSVSaver saver = new CSVSaver();
            saver.setInstances(dataInstance);
            saver.setFile(tempFIle);
            saver.writeBatch();
        } catch (IOException e) {

            log.error("Error occurred:"+e.getMessage());
        }
        return status;
    }

    public int whiteningData(){
        System.out.println("whiteningData");

        int nums = 0;
        try {

            if(tempFIle!=null && tempFIle.exists()) {

                csv.setSource(tempFIle);
            }
            else{

                csv.setSource(csvFile);
            }
            Instances instances = csv.getDataSet();
            if(instances.numAttributes() > 10) {
                instances.setClassIndex(instances.numAttributes() - 1);
                RandomProjection random = new RandomProjection();
                random.setDistribution(new SelectedTag(RandomProjection.GAUSSIAN,RandomProjection.TAGS_DSTRS_TYPE));
                reducedDiemensionFile = new File(csvFile.getParent() + "/tempwhite.csv");
                if (!reducedDiemensionFile.exists()) {

                    reducedDiemensionFile.createNewFile();
                }
                // CSVSaver saver = new CSVSaver();
                /// saver.setFile(reducedDiemensionFile);
                random.setInputFormat(instances);
                //saver.setRetrieval(AbstractSaver.INCREMENTAL);
                BufferedWriter writer = new BufferedWriter(new FileWriter(reducedDiemensionFile));
                for (int i = 0; i < instances.numInstances(); i++) {

                    random.input(instances.instance(i));
                    random.setNumberOfAttributes(10);
                    random.setReplaceMissingValues(true);
                    writer.write(random.output().toString());
                    writer.newLine();
                    //saver.writeIncremental(random.output());
                }
                writer.flush();
                writer.close();
                nums = random.getNumberOfAttributes();
            }else {

                nums = instances.numAttributes();
            }
        } catch (IOException e) {

            log.error("Error occurred:"+e.getMessage());
        } catch (Exception e) {

            log.error("Error occurred:"+e.getMessage());
        }
        return nums;
    }

    public boolean saveAsLibSVMFormat(){
        System.out.println("saveAsLibSVMFormat");

        boolean status = false;
        try {

            String fileName = csvFile.getName();
            File svm = new File(csvFile.getParent()+"/"+fileName+"lib_svm");
            if(!svm.exists()){

                svm.createNewFile();
            }
            LibSVMSaver saver = new LibSVMSaver();
            saver.setFile(svm);
            Instances instances = csv.getDataSet();
            saver.setInstances(instances);
            saver.writeBatch();
            status = true;
        } catch (IOException e) {

            log.error("Error occurred:"+e.getMessage());
        }
        return status;
    }

    private void writeToNewFile(String directory){

        try {

            BufferedReader reader = new BufferedReader(new FileReader(outputFile));
            PrintWriter writer = new PrintWriter(new File(directory+"norm"+csvFile.getName()));
            String output = "";
            int count = 0;
            while ((output = reader.readLine())!=null){

                if(count==0){

                    count++;
                    continue;
                }
                writer.println(output);
            }
            writer.flush();
            writer.close();
            reader.close();
        } catch (FileNotFoundException e) {

            log.error("Error occurred:"+e.getMessage());
        } catch (IOException e) {

            log.error("Error occurred:"+e.getMessage());
        }
    }
}
