package com.sliit.knnanalysis.features;

public class Length extends Feature{

    private final double length;
    
    public Length(double length) {
        super(String.valueOf(length));
        this.length = length;
    }

    @Override
    void calculateDistances() {
        setDistance(length);
    }
    
}
