/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

/**
 *
 * @author Heshani
 */
public class TrafficAnalyserPanel extends javax.swing.JPanel implements PacketReceiver {

    private NetworkInterface selectedDevice;
    private File file;
    private FileWriter fileWriter;
    private final String header;

    /**
     * Creates new form trafficAnalyser
     */
    public TrafficAnalyserPanel() {
        initComponents();
        
        header = "source,destination,protocol,length \n";
        
        try {
            String filePath = locationText.getText() + "" + fileNameText.getText();
            file = new File(filePath);
            fileWriter = new FileWriter(file);
        } catch (IOException ex) {
            Logger.getLogger(TrafficAnalyserPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public TrafficAnalyserPanel(NetworkInterface[] networkInterfaces, String deviceName) {
        this();

        for (int i = 0; i < networkInterfaces.length; i++) {

            if (networkInterfaces[i].name.equals(deviceName)) {
                selectedDevice = networkInterfaces[i];
                break;
            }
        }

        if (selectedDevice != null) {
            deviceLabel.setText(selectedDevice.name);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deviceLabel = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        timeText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fileNameText = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        packetTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        locationText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(905, 509));
        setMinimumSize(new java.awt.Dimension(905, 509));
        setPreferredSize(new java.awt.Dimension(905, 509));

        deviceLabel.setText("Select Device");

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Time :");

        timeText.setText("10");

        jLabel2.setText("File Name:");

        fileNameText.setText("FeatureTable.csv");

        packetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Packet"
            }
        ));
        jScrollPane2.setViewportView(packetTable);

        jLabel3.setText("Save :");

        locationText.setText("D:/");

        jLabel4.setText("secs");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deviceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 875, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(timeText, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(17, 17, 17)
                        .addComponent(startButton)
                        .addGap(46, 46, 46)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(locationText)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(deviceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(jLabel1)
                    .addComponent(timeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(fileNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(locationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed

        try {
            fileWriter.write(header);

            //1000 - mili, 10000mili sec = 10sec
            int miliSecs = Integer.parseInt(timeText.getText()) * 1000;

            //openDevice(open specified device,man no of bytes captured at once,promiscous mode,timeout for capture in milliseconds)
            //-1 to continue capturing packets infinitely
            JpcapCaptor captor = JpcapCaptor.openDevice(selectedDevice, -1, false, miliSecs);

            //processPacket() supports timeout and non blocking mode, no of captures -1 infinite captures
            captor.processPacket(-1, new TrafficAnalyserPanel());

            captor.close();

            fileWriter.close();

        } catch (IOException ex) {
            Logger.getLogger(TrafficAnalyserPanel.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_startButtonActionPerformed

    @Override
    public void receivePacket(Packet packet) {

        //System.out.println(packet);
        if (packet instanceof IPPacket) {
            IPPacket ipPacket = (IPPacket) packet;

            System.out.println("IP---------");
            System.out.println(ipPacket);

//            byte header[] = packet.header;
//            byte data[] = packet.data;
//            int caplen = packet.caplen;
//            DatalinkPacket datalink = packet.datalink;
//            int length = packet.len;
//            long sec = packet.sec;
//            long usec = packet.usec;
//
//            System.out.println("source ip " + ipPacket.src_ip + " " + ipPacket.src_ip.getHostAddress() + " " + ipPacket.src_ip.getHostName());
//            System.out.println("dest ip " + ipPacket.dst_ip + " " + ipPacket.dst_ip.getHostAddress() + " " + ipPacket.dst_ip.getHostName());
//            System.out.println("options " + ipPacket.options + " flow_label " + ipPacket.flow_label + " version " + ipPacket.version + " ident " + ipPacket.ident);
//            System.out.println("protocol " + ipPacket.protocol);
//            System.out.println("hop_limit " + ipPacket.hop_limit);
//            System.out.println("caplen " + caplen);
//            System.out.println("datalink " + datalink);
//            System.out.println("length " + length);
//            System.out.println("sec " + sec);
//            System.out.println("usec " + usec + "\n");
        } else if (packet instanceof ARPPacket) {
            ARPPacket arpPacket = (ARPPacket) packet;

            System.out.println("ARP---------------");
            System.out.println(arpPacket);
        }

        //writing to file
        try {
            fileWriter.write(packet.toString() + "\n");
            fileWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(TrafficAnalyserPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel deviceLabel;
    private javax.swing.JTextField fileNameText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField locationText;
    private javax.swing.JTable packetTable;
    private javax.swing.JButton startButton;
    private javax.swing.JTextField timeText;
    // End of variables declaration//GEN-END:variables

}
