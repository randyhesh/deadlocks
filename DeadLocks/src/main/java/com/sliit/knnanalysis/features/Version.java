package com.sliit.knnanalysis.features;

public class Version extends Feature{
    
    private final double version;
    
    public Version(double version) {
        super(String.valueOf(version));
        this.version = version;
    }
    
    @Override
    void calculateDistances() {
        setDistance(version);
    }
}
