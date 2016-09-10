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
public class Destination  extends Feature{
    
    private final Destinations DESTINATION;
    
    public Destination(Destinations destination) {
        super(destination.name());
        this.DESTINATION = destination;
    }

    public enum Destinations {
        destination1,
        destination2,
        destination3,
        destination4
    }

    @Override
    void calculateDistances() {
        setDistance(DESTINATION.ordinal());
    }
}
