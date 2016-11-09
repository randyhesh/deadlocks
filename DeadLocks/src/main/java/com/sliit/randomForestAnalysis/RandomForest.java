/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.randomForestAnalysis;

import com.sliit.views.PredictorPanel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import scala.Tuple2;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

/**
 *
 * @author indu
 */
public class RandomForest implements Serializable {

    /**
     * get accuracy of network traffic using random forest classification
     * @param pnumTrees
     * @param pmaxDepth
     * @param pmaxBins
     * @param pseed
     * @return 
     */
    public List<String> load(int pnumTrees, int pmaxDepth, int pmaxBins, int pseed) {
        SparkConf sparkConf = new SparkConf()
                .setAppName("DeadLocks")
                .setMaster("local").set("spark.driver.allowMultipleContexts", "true");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        JavaRDD<LabeledPoint> trainingData = MLUtils.loadLibSVMFile(jsc.sc(), PredictorPanel.locationText.getText()).toJavaRDD();
        JavaRDD<LabeledPoint> testData = MLUtils.loadLibSVMFile(jsc.sc(), PredictorPanel.modalText.getText()).toJavaRDD();

        // Train a RandomForest model.
        // Empty categoricalFeaturesInfo indicates all features are continuous.
        Integer numClasses = 3;
        HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<>();
        Integer numTrees = pnumTrees;
        String featureSubsetStrategy = "auto";
        String impurity = "gini";
        Integer maxDepth = pmaxDepth;
        Integer maxBins = pmaxBins;
        Integer seed = pseed;

        final RandomForestModel model = org.apache.spark.mllib.tree.RandomForest.trainClassifier(trainingData, numClasses,
                categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins,
                seed);

        // Evaluate model on test instances and compute test error
        JavaPairRDD<Object, Object> predictionAndLabel
                = testData.mapToPair(new PairFunction<LabeledPoint, Object, Object>() {
                    @Override
                    public Tuple2<Object, Object> call(LabeledPoint p) throws Exception {
                        return new Tuple2<>(model.predict(p.features()), p.label());
                    }
                });

        //calculate test error
        Double prediction
                = 100.0 * predictionAndLabel.filter(new Function<Tuple2<Object, Object>, Boolean>() {
                    @Override
                    public Boolean call(Tuple2<Object, Object> pl) {
                        return pl._1().equals(pl._2());
                    }
                }).count() / testData.count();

        MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabel.rdd());

        List<String> output = new ArrayList<String>();
        output.add(" " + prediction);
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

        return output;
    }
}
