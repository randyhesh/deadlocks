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
import jpcap.packet.TCPPacket;

/**
 *
 * @author Heshani
 */
public class TrafficAnalyserPanel extends javax.swing.JPanel implements PacketReceiver {

    private NetworkInterface selectedDevice;
    private File file;
    private FileWriter fileWriter;
    private final String header;
    private DefaultTableModel dtmPacketTable;
    ArrayList<Object> dataList;

    /**
     * Creates new form trafficAnalyser
     */
    public TrafficAnalyserPanel() {

        initComponents();
        header = "time,source,destination,protocol,length,caplen,hop_limit,version,datalink \n";
        dtmPacketTable = (DefaultTableModel) packetsDisplayTable.getModel();
        dataList = new ArrayList<>();

        try {
            String filePath = locationText.getText() + "/" + fileNameText.getText();
            file = new File(filePath);
            fileWriter = new FileWriter(file);

            fileWriter.write(header);
        } catch (IOException ex) {
            Logger.getLogger(TrafficAnalyserPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public TrafficAnalyserPanel(NetworkInterface[] networkInterfaces, String deviceName) {
        this();

        for (int i = 0; i < networkInterfaces.length; i++) {

            if (networkInterfaces[i].name.equals(deviceName)) {
                selectedDevice = networkInterfaces[i];
                System.out.println(networkInterfaces[i].name);
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
        packetsDisplayTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        locationText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(905, 509));
        setMinimumSize(new java.awt.Dimension(905, 509));
        setPreferredSize(new java.awt.Dimension(905, 509));

        deviceLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        deviceLabel.setForeground(new java.awt.Color(51, 204, 255));
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

        packetsDisplayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Time", "Source", "Destination", "Protocol", "Legth", "Caplen", "Hop limit", "Version", "Datalink"
            }
        ));
        jScrollPane2.setViewportView(packetsDisplayTable);

        jLabel3.setText("Save :");

        locationText.setText("D:\\deadlocks\\data");

        jLabel4.setText("secs");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(deviceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
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
                            .addComponent(locationText))))
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
            //1000 - mili, 10000mili sec = 10sec
            int miliSecs = Integer.parseInt(timeText.getText()) * 1000;

            //openDevice(open specified device,man no of bytes captured at once,promiscous mode,timeout for capture in milliseconds)
            //-1 to continue capturing packets infinitely
            JpcapCaptor captor = JpcapCaptor.openDevice(selectedDevice, -1, false, miliSecs);

            //processPacket() supports timeout and non blocking mode, no of captures -1 infinite captures
            captor.processPacket(-1, new TrafficAnalyserPanel());

            captor.close();

            fileWriter.close();

            for (int i = 0; i < dataList.size(); i++) {
                System.out.println("data lsit---");

                System.out.println(dataList.get(i));
            }

        } catch (IOException ex) {
            Logger.getLogger(TrafficAnalyserPanel.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_startButtonActionPerformed

    @Override
    public void receivePacket(Packet packet) {

        String dataPacket = "";
        Object[] ob = new Object[9];

        //System.out.println(packet);               
        int caplen = packet.caplen;
        byte data[] = packet.data;
        DatalinkPacket datalink = packet.datalink;
        byte header[] = packet.header;
        int length = packet.len;
        long sec = packet.sec;
        long usec = packet.usec;

        if (packet instanceof IPPacket) {
            IPPacket ipPacket = (IPPacket) packet;

            System.out.println("IP---------");
//            System.out.println(ipPacket);
//
//            System.out.println("source ip " + ipPacket.src_ip + " " + ipPacket.src_ip.getHostAddress() + " " + ipPacket.src_ip.getHostName());
//            System.out.println("dest ip " + ipPacket.dst_ip + " " + ipPacket.dst_ip.getHostAddress() + " " + ipPacket.dst_ip.getHostName());
//            System.out.println("options " + ipPacket.options + " flow_label " + ipPacket.flow_label + " version " + ipPacket.version + " ident " + ipPacket.ident);
//            System.out.println("protocol " + ipPacket.protocol);
//            System.out.println("hop_limit " + ipPacket.hop_limit);

            dataPacket = usec + "," + ipPacket.src_ip + "," + ipPacket.dst_ip + ","
                    + ipPacket.protocol + "," + length + "," + caplen + ","
                    + ipPacket.hop_limit + "," + ipPacket.version + "," + datalink + "\n";

            ob[0] = usec;
            ob[1] = ipPacket.src_ip;
            ob[2] = ipPacket.dst_ip;
            ob[3] = ipPacket.protocol;
            ob[4] = length;
            ob[5] = caplen;
            ob[6] = ipPacket.hop_limit;
            ob[7] = ipPacket.version;
            ob[8] = datalink;

            //ob = {usec, ipPacket.src_ip, ipPacket.dst_ip, ipPacket.protocol, length, caplen, ipPacket.hop_limit, ipPacket.version, datalink};           
        }

        if (packet instanceof ARPPacket) {
            ARPPacket arpPacket = (ARPPacket) packet;

            System.out.println("ARP---------------");
//            System.out.println(arpPacket);
//
//            System.out.println("sender_hardaddr " + arpPacket.sender_hardaddr + " " + arpPacket.sender_protoaddr + " hw Addr " + arpPacket.getSenderHardwareAddress());
//            System.out.println("target_hardaddr " + arpPacket.target_hardaddr + " " + arpPacket.target_protoaddr + " hw Addr " + arpPacket.getTargetHardwareAddress());
//            System.out.println("hardtype " + arpPacket.hardtype + " hlen " + arpPacket.hlen + " len " + arpPacket.len + " plen " + arpPacket.plen);
//            System.out.println("protocol " + arpPacket.getSenderProtocolAddress() + " " + arpPacket.getTargetProtocolAddress());
//            System.out.println("prototype " + arpPacket.prototype);
//            System.out.println("operation " + arpPacket.operation);

            dataPacket = usec + "," + arpPacket.getSenderProtocolAddress() + ","
                    + arpPacket.getTargetProtocolAddress() + "," + "ARP" + ","
                    + length + "," + caplen + "," + "" + "," + "" + "," + datalink + "\n";

            ob[0] = usec;
            ob[1] = arpPacket.getSenderProtocolAddress();
            ob[2] = arpPacket.getTargetProtocolAddress();
            ob[3] = "ARP";
            ob[4] = length;
            ob[5] = caplen;
            ob[6] = "";
            ob[7] = "";
            ob[8] = datalink;

            //Object ob[] = {usec, arpPacket.getSenderProtocolAddress(), arpPacket.getTargetProtocolAddress(), "ARP", length, caplen, "", "", datalink};
        }

         if (packet instanceof TCPPacket) {
             
         }
        
//        System.out.println("caplen " + caplen); //Captured length
//        System.out.println("data " + data); //Packet data (excluding the header)
//        System.out.println("datalink " + datalink); //Datalink layer header
//        System.out.println("header " + header); //Header data
//        System.out.println("length " + length); //Length of this packet
//        System.out.println("sec " + sec); // Captured timestamp (sec)
//        System.out.println("usec " + usec + "\n"); //Captured timestamp (micro sec)
        System.out.println(dataPacket);
        dataList.add(ob);

        //writing to file
        try {
            fileWriter.write(dataPacket);
            fileWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(TrafficAnalyserPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //send data to GUI
//        java.awt.EventQueue.invokeLater(
//                new Runnable() {
//            public void run() {
//                addDataToTable(ob);
//            }
//        });
    }

    void addDataToTable(Object ob[]) {
        System.out.println("added");
        dtmPacketTable.addRow(ob);
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
    private javax.swing.JTable packetsDisplayTable;
    private javax.swing.JButton startButton;
    private javax.swing.JTextField timeText;
    // End of variables declaration//GEN-END:variables

}
