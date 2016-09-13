package com.sliit.knnanalysis.features;

public class Source extends Feature{

    private final double SOURCE;
    
    public Source(String source) {
        super(source);
        if("0.0.0.0".equals(source) || "192.168.1.6".equals(source)){
            this.SOURCE = 0;
        }else{
            this.SOURCE = Double.parseDouble(source);
        }
    }

    @Override
    void calculateDistances() {
        setDistance(SOURCE);
    }
    
}
