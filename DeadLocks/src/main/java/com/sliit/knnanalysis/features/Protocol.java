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
public class Protocol extends Feature {

    private final Protocols PROTOCOL;

    public Protocol(Protocols protocol) {
        super(protocol.name());
        PROTOCOL = protocol;
    }

    public enum Protocols {
        IPv4,
        IPv6
    }

    @Override
    void calculateDistances() {
        setDistance(PROTOCOL.ordinal());
    }

}
