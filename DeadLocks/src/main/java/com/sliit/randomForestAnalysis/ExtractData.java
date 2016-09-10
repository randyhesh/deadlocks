/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.randomForestAnalysis;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

;
import org.python.core.PyInstance;
import org.python.util.PythonInterpreter;

/**
 *
 * @author indu
 */


public class ExtractData implements Serializable  {

    public void extract() throws IOException{
        System.out.println("ggggggggg");
// set up the command and parameter
        String pythonScriptPath = "extract.py";
        String[] cmd = new String[2];
        cmd[0] = "python"; // check version of installed python: python -V
        cmd[1] = pythonScriptPath;
System.out.println("ttttttt");
// create runtime to execute external command
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(cmd);

// retrieve output from python script
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        while ((line = bfr.readLine()) != null) {
// display each output line form python script
            System.out.println(line);
        }
    }

}
