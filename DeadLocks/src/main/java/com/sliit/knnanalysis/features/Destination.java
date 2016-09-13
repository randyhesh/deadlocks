package com.sliit.knnanalysis.features;

public class Destination extends Feature{
    
    private final double DESTINATION;
    
    public Destination(String destination) {
        super(destination);
        if("0.0.0.0".equals(destination) || "224.0.0.22".equals(destination)){
            this.DESTINATION = 0;
        }else{
            this.DESTINATION = Double.parseDouble(destination);
        }
    }

    @Override
    void calculateDistances() {
        setDistance(DESTINATION);
    }
}
