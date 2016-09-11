/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.svmanalysis;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;

/**
 *
 * @author Heshani
 */
public class S {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Your Application Name").setMaster("local").set("spark.executor.memory", "1g");
        SparkContext sc = new SparkContext(conf);
        String path = "D:/deadlocks/data/a.csv";
        JavaRDD<LabeledPoint> points = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();

        JavaRDD<LabeledPoint> training = points.sample(false, 0.8, 0L).cache();

        JavaRDD<LabeledPoint> testing = points.subtract(training);

        SVMModel model = SVMWithSGD.train(training.rdd(), 100);

        model.clearThreshold();

        for (LabeledPoint point : testing.toArray()) {
            Double score = model.predict(point.features());

            System.out.println("score = " + score);//<- all these are negative numbers, seemingly between 0 and -2
        }
    }
}
