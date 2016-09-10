/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.svmanalysis;

import java.util.ArrayList;
import java.util.List;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

/**
 *
 * @author Heshani
 */
public class B {

    public static void main(String[] args) {

        /**
         * *************************************************************************
         * First get a list of devices on this system
         * ************************************************************************
         */
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
        StringBuilder errbuf = new StringBuilder(); // For any error msgs
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r != Pcap.OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s",
                    errbuf.toString());
            return;
        }
        System.out.println("Network devices found:");
        int i = 0;
        for (PcapIf device : alldevs) {
            String description = (device.getDescription() != null) ? device
                    .getDescription() : "No description available";
            System.out.printf("#%d: %s [%s]\n", i++, device.getName(),
                    description);
        }
        PcapIf device = alldevs.get(0); // Get first device in list
        System.out.println("Choosing  on your behalf " + device.getName());

        /**
         * *************************************************************************
         * Second we open up the selected device
         * ************************************************************************
         */
        int snaplen = 64 * 1024; // Capture all packets, no trucation
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
        int timeout = 10 * 1000; // 10 seconds in millis
        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
        if (pcap == null) {
            System.err.printf("Error while opening device for capture: "
                    + errbuf.toString());
            return;
        }

        /**
         * *************************************************************************
         * Third we create a packet handler which will receive packets from the
         * libpcap loop.
         * ************************************************************************
         */
        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
            int i=0;
            public void nextPacket(PcapPacket packet, String user) {

                i++;
                
                System.out.println("================START PACKET=================" + i);

                //ip packet
                Ip4 ip = new Ip4();

                if (packet.hasHeader(ip) == true) {

                    System.out.println("-------------IP-------------------");
                    System.out.println("hlen " + ip.hlen());
                    System.out.println("source " + FormatUtils.ip(ip.source()) + " dest " + FormatUtils.ip(ip.destination()) + " version " + ip.version());
                }

                //tcp 
                Tcp tcp = new Tcp();
                if (packet.hasHeader(tcp) == true) {

                    System.out.println("----------------TCP----------------");
                    System.out.println("hlen " + tcp.hlen());
                    System.out.println("source " + tcp.source() + " dest " + tcp.destination());
                }

                Udp udp = new Udp();
                if (packet.hasHeader(udp) == true) {

                    System.out.println("----------------UDP----------------");
                    System.out.println("hlen " + udp.getHeaderLength());
                    System.out.println("source " + udp.source() + " dest " + udp.destination());
                }

                Arp arp = new Arp();
                if (packet.hasHeader(arp) == true) {
                    System.out.println("----------------ARP----------------");
                    System.out.println("hlen " + arp.hlen());

                }

                Icmp icmp = new Icmp();
                if (packet.hasHeader(icmp) == true) {

                    System.out.println("----------------ICMP----------------");
                    System.out.println("hlen " + icmp.getHeaderLength());

                }

                
                System.out.println("wirelen " + packet.getCaptureHeader().wirelen());
                System.out.println("caplen " + packet.getCaptureHeader().caplen());
                System.out.println("time ms " + packet.getCaptureHeader().timestampInMillis());

                System.out.println("================END PACKET=================");
            }
        };

        /**
         * *************************************************************************
         * Fourth we enter the loop and tell it to capture 10 packets. The loop
         * method does a mapping of pcap.datalink() DLT value to JProtocol ID,
         * which is needed by JScanner. The scanner scans the packet buffer and
         * decodes the headers. The mapping is done automatically, although a
         * variation on the loop method exists that allows the programmer to
         * sepecify exactly which protocol ID to use as the data link type for
         * this pcap interface.
         * ************************************************************************
         */
        pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, "jNetPcap");

        /**
         * *************************************************************************
         * Last thing to do is close the pcap handle
         * ************************************************************************
         */
        pcap.close();
    }
}
