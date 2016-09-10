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
public class Length extends Feature {

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
