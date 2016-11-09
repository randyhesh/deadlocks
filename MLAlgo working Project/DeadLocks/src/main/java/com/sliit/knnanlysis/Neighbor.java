package com.sliit.knnanlysis;

public class Neighbor {

    private Instance instance;
    private double distance;

    public Neighbor(Instance instance, double distance) {
        this.instance = instance;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public Instance getInstance() {
        return instance;
    }
}
