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
public class Source extends Feature{

    private final Sources SOURCE;
    
    public Source(Sources source) {
        super(source.name());
        this.SOURCE = source;
    }

    public enum Sources {
        source1,
        source2,
        source3,
        source4
    }

    @Override
    void calculateDistances() {
        setDistance(SOURCE.ordinal());
    }
    
}
