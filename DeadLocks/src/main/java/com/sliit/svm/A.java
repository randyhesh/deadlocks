/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.svm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

/**
 *
 * @author Heshani
 */
public class A {

    public static void main(String[] args) throws IOException {
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
        StringBuilder errbuf = new StringBuilder(); // For any error msgs
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.out.println("Can't read list of devices, error is " + errbuf.toString());

            return;
        }
        System.out.println("Network devices found:");
        int i = 0;
        for (PcapIf device : alldevs) {
            String description = (device.getDescription() != null) ? device
                    .getDescription() : "No description available";

            System.out.println("name " + device.getName() + " i " + i++ + " desc " + description);
        }
    }
}
