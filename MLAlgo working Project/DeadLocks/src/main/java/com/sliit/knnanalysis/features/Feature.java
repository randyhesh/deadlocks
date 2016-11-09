package com.sliit.knnanalysis.features;

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
