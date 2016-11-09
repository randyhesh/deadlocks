/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.svmanalysis;

import java.io.Serializable;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple2;

/**
 *
 * @author Heshani
 */
public class SvmAnalyser implements Serializable {

    private Double auRoc;
    
    public SvmAnalyser() {

    }

    /**
     * perform analysis using SVM algorithm     
     * @param dataset
     * @return
     */
     public String perfomeAnalysis(String trainDataset, String testDataset) {

        String output = "";

        SparkConf conf = new SparkConf().setAppName("DeadLocks").setMaster("local").set("spark.executor.memory", "1g");        
        SparkContext sc = new SparkContext(conf);

        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, trainDataset).toJavaRDD();
        JavaRDD<LabeledPoint> testData = MLUtils.loadLibSVMFile(sc, testDataset).toJavaRDD();
        
        JavaRDD<LabeledPoint> training = data.sample(false, 1.0, 11L);
        training.cache();

        JavaRDD<LabeledPoint> test = testData.sample(false, 1.0, 11L);

        // Run training algorithm to build the model.
        int numIterations = 100;
        final SVMModel model = SVMWithSGD.train(training.rdd(), numIterations);

        // Clear the default threshold.
        model.clearThreshold();

        // Compute raw scores on the test set.
        JavaRDD<Tuple2<Object, Object>> scoreAndLabels = test.map(
                new Function<LabeledPoint, Tuple2<Object, Object>>() {
            public Tuple2<Object, Object> call(LabeledPoint p) {
                Double score = model.predict(p.features());
                return new Tuple2<Object, Object>(score, p.label());
            }
        });

        //calculate test error
        Double prediction
                = 100.0 * scoreAndLabels.filter(new Function<Tuple2<Object, Object>, Boolean>() {
                    @Override
                    public Boolean call(Tuple2<Object, Object> pl) {
                        return !pl._1().equals(pl._2());
                    }
                }).count() / testData.count();

        System.out.println("Accuracy " + prediction);
        output += "Accuracy : " + prediction + "\n";

        //Get evaluation metrics.
        BinaryClassificationMetrics metrics = new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels));
        this.auRoc = metrics.areaUnderROC();

        // Precision by threshold
        JavaRDD<Tuple2<Object, Object>> precision = metrics.precisionByThreshold().toJavaRDD();
        System.out.println("Precision by threshold: " + precision.collect());
        output += "Precision by threshold : " + precision.collect() + "\n";

        // Recall by threshold
        JavaRDD<Tuple2<Object, Object>> recall = metrics.recallByThreshold().toJavaRDD();
        System.out.println("Recall by threshold: " + recall.collect());
        output += "Precision by threshold : " + precision.collect() + "\n";

        // F Score by threshold
        JavaRDD<Tuple2<Object, Object>> f1Score = metrics.fMeasureByThreshold().toJavaRDD();
        System.out.println("F1 Score by threshold: " + f1Score.collect());
        output += "F1 Score by threshold: " + f1Score.collect() + "\n";

        JavaRDD<Tuple2<Object, Object>> f2Score = metrics.fMeasureByThreshold(2.0).toJavaRDD();
        System.out.println("F2 Score by threshold: " + f2Score.collect());
        output += "F2 Score by threshold: " + f2Score.collect() + "\n";

        // Precision-recall curve
        JavaRDD<Tuple2<Object, Object>> prc = metrics.pr().toJavaRDD();
        System.out.println("Precision-recall curve: " + prc.collect());
        output += "Precision-recall curve: " + prc.collect() + "\n";

        // ROC Curve
        JavaRDD<Tuple2<Object, Object>> roc = metrics.roc().toJavaRDD();
        System.out.println("ROC curve: " + roc.collect());
        output += "ROC curve: " + roc.collect() + "\n";

        // AUPRC
        System.out.println("Area under precision-recall curve = " + metrics.areaUnderPR());
        output += "Area under precision-recall curve : " + metrics.areaUnderPR() + "\n";

        // AUROC
        System.out.println("Area under ROC = " + metrics.areaUnderROC());
        output += "Area under ROC: " + metrics.areaUnderROC() + "\n";
        sc.stop();

        return output;
    }

    public void setRoc(Double auRoc) {
        this.auRoc = auRoc;
    }

    public Double getauRoc() {
        return auRoc;
    }
}
