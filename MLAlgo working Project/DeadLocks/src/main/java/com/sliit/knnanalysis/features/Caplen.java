package com.sliit.knnanalysis.features;

public class Caplen extends Feature{
    
    private final double caplen;
    
    public Caplen(double caplen) {
        super(String.valueOf(caplen));
        this.caplen = caplen;
    }

    @Override
    void calculateDistances() {
        setDistance(caplen);
    }
    
}
