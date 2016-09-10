/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.randomForestAnalysis;

import au.com.bytecode.opencsv.CSVReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import scala.Tuple2;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
//import org.apache.spark.ml.classification.RandomForestClassificationModel;
//import static org.apache.spark.ml.util.BaseReadWrite$class.sc;
import org.apache.spark.sql.Column;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 *
 * @author indu
 */
public class RandomForest implements Serializable {

    public List<String> load(int pnumTrees, int pmaxDepth, int pmaxBins, int pseed) {
        SparkConf sparkConf = new SparkConf()
                .setAppName("DeadLocks")
                .setMaster("local").set("spark.driver.allowMultipleContexts", "true");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

// Load and parse the data file.
        String datapath = "Data.txt";
//String datapath1 = "Test";
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), datapath).toJavaRDD();
//JavaRDD<LabeledPoint> testData = MLUtils.loadLibSVMFile(jsc.sc(), datapath1).toJavaRDD();
// Split the data into training and test sets (30% held out for testing)
        JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[]{0.7, 0.3});
//

        JavaRDD<LabeledPoint> trainingData = splits[0];
        JavaRDD<LabeledPoint> testData = splits[1];
        System.out.println("eee");
// Train a RandomForest model.
// Empty categoricalFeaturesInfo indicates all features are continuous.
        Integer numClasses = 3;
        HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<>();
        Integer numTrees = pnumTrees; // Use more in practice.
        String featureSubsetStrategy = "auto"; // Let the algorithm choose.
        String impurity = "gini";
        Integer maxDepth = pmaxDepth;
        Integer maxBins = pmaxBins;
        Integer seed = pseed;

        final RandomForestModel model = org.apache.spark.mllib.tree.RandomForest.trainClassifier(trainingData, numClasses,
                categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins,
                seed);
        System.out.println("eee");

// Evaluate model on test instances and compute test error
        JavaPairRDD<Object, Object> predictionAndLabel
                = testData.mapToPair(new PairFunction<LabeledPoint, Object, Object>() {
                    @Override
                    public Tuple2<Object, Object> call(LabeledPoint p) throws Exception {
                        return new Tuple2<>(model.predict(p.features()), p.label());
                    }
                });
        Double testErr
                = 100.0 * predictionAndLabel.filter(new Function<Tuple2<Object, Object>, Boolean>() {
                    @Override
                    public Boolean call(Tuple2<Object, Object> pl) {
                        return pl._1().equals(pl._2());
                    }
                }).count() / testData.count();

        MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabel.rdd());
        //prints like the following

//
        List<String> output = new ArrayList<String>();
        output.add(" " + testErr);
        output.add("  " + model.toDebugString());
        output.add("" + metrics.confusionMatrix());

        output.add("" + metrics.precision());
        output.add("" + metrics.recall());
        output.add("" + metrics.fMeasure());
        output.add("F1 Score = ");

        output.add("" + metrics.weightedPrecision());
        output.add("" + metrics.weightedRecall());
        output.add("" + metrics.weightedFMeasure());
        output.add("" + metrics.weightedFalsePositiveRate());
        output.add("" + metrics.weightedTruePositiveRate());
        System.out.println("================================================================================================");

        System.out.println("Learned classification forest model:\n" + model.toDebugString());
        System.out.println("confusion matrix :\n" + metrics.confusionMatrix());
// System.out.println("Confusion matrix: \n"+metrics.confusionMatrix()+"\n");
// Save and load model
//model.save(jsc.sc(), "target/tmp/myRandomForestClassificationModel");
//RandomForestModel sameModel = RandomForestModel.load(jsc.sc(),
//  "target/tmp/myRandomForestClassificationModel");
        return output;
    }
}
