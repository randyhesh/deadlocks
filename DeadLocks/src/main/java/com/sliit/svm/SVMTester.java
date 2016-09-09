/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.svm;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.optimization.L1Updater;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple2;

/**
 *
 * @author Heshani
 */
public class SVMTester {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Your Application Name").setMaster("local").set("spark.executor.memory", "1g");
        //SparkConf conf = new SparkConf().setAppName("SVM Classifier Example");
        SparkContext sc = new SparkContext(conf);
        String path = "D:/deadlocks/data/a.csv";
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();

        // Split initial RDD into two... [60% training data, 40% testing data].
        JavaRDD<LabeledPoint> training = data.sample(false, 0.6, 11L);
        training.cache();
        JavaRDD<LabeledPoint> test = data.subtract(training);

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
                }
        );

        //Get evaluation metrics.
        BinaryClassificationMetrics metrics = new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels));
        double auROC = metrics.areaUnderROC();

        System.out.println("Area under ROC = " + auROC);

        //https://github.com/apache/spark/blob/master/examples/src/main/java/org/apache/spark/examples/mllib/JavaSVMWithSGDExample.java
        //sc.stop();
//        SVMWithSGD svmAlg = new SVMWithSGD();
//        svmAlg.optimizer().setNumIterations(200).setRegParam(0.1).setUpdater(new L1Updater());
//        final SVMModel modelL1 = svmAlg.run(training.rdd());
        //http://blogs.quovantis.com/image-classification-using-apache-spark-with-linear-svm/        
        // Save and load model
//        model.save(sc, "myModelPath");
//        SVMModel sameModel = SVMModel.load(sc, "myModelPath");
    }
}
