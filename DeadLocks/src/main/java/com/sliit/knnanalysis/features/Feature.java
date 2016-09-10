/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.knnanalysis.features;

/**
 *
 * @author Heshani
 */

public abstract class Feature {
    private String featureName;
    private double distance;

    public Feature(String name) {
        this.featureName = name;
    }
    
    public String getFeatureName() {
        return featureName;	
    }
    
    public double getDistance(){
        calculateDistances();
        return this.distance;
    }
    
    void setDistance(double distance){
        this.distance = distance;
    }
    
    abstract void calculateDistances();
}
