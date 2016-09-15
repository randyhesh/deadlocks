/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.filegen;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 *
 * @author heshanih
 */
public class CSV2Arff {

    void convertCSV2Arff(String csvFile, String arffFile) {

        try {
            // load CSV
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File(csvFile));
            Instances data = loader.getDataSet();

            // save ARFF
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File(arffFile));
            //saver.setDestination(new File(arffPath));
            saver.writeBatch();
        } catch (IOException ex) {
            Logger.getLogger(CSV2Arff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
