package com.sliit.knnanalysis.features;

public class Hlen extends Feature{

    private final double hlen;
    
    public Hlen(double hlen) {
        super(String.valueOf(hlen));
        this.hlen = hlen;
    }

    @Override
    void calculateDistances() {
        setDistance(hlen);
    }
    
}
