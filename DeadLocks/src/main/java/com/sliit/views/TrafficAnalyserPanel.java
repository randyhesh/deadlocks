/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

/**
 *
 * @author Heshani
 */
public class TrafficAnalyserPanel extends javax.swing.JPanel {

    private PcapIf selectedDevice;
    private File file;
    private FileWriter fileWriter;
    private final String header;
    private DefaultTableModel dtmPacketTable;
    private String filePath;
    private int counter = 0;

    /**
     * Creates new form trafficAnalyser
     */
    public TrafficAnalyserPanel() {

        initComponents();
        header = "time,source,destination,protocol,length,caplen,hlen,version,output \n";

    }

    public TrafficAnalyserPanel(List<PcapIf> networkInterfaces, String deviceName) {
        this();

        for (int i = 0; i < networkInterfaces.size(); i++) {

            PcapIf device = networkInterfaces.get(i);

            if (device.getName().equals(deviceName)) {
                selectedDevice = device;
                System.out.println(device.getName());
                break;
            }
        }
        if (selectedDevice != null) {
            deviceLabel.setText(selectedDevice.getName() + " - " + selectedDevice.getDescription());
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
        jScrollPane2 = new javax.swing.JScrollPane();
        packetsDisplayTable = new javax.swing.JTable();
        locationText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();

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

        packetsDisplayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Time", "Source", "Destination", "Protocol", "Legth", "Caplen", "Hlen", "Version"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(packetsDisplayTable);

        locationText.setText("D:\\deadlocks\\data\\FeatureTable.csv");

        jLabel4.setText("secs");

        progressBar.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file-explorer-icon.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(locationText, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeText, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startButton))
                            .addComponent(deviceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 875, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(deviceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(jLabel1)
                    .addComponent(timeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(locationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed

        dtmPacketTable = (DefaultTableModel) packetsDisplayTable.getModel();
        filePath = locationText.getText();

        try {
            file = new File(filePath);
            fileWriter = new FileWriter(file);

            fileWriter.write(header);
        } catch (IOException ex) {
            Logger.getLogger(TrafficAnalyserPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        dtmPacketTable.setRowCount(0);
        progressBar.setValue(0);

        //packet capturing thread
        new Thread(new Runnable() {
            public void run() {
                try {

                    double start = System.currentTimeMillis();
                    double timeoutsec = Double.parseDouble(timeText.getText());
                    int timeoutms = Integer.parseInt(timeText.getText()) * 1000;
                    System.out.println("Timeout " + timeoutms + "ms");

                    // Capture all packets, no trucation
                    int snaplen = 64 * 1024;
                    // capture all packets
                    int flags = Pcap.MODE_PROMISCUOUS;

                    StringBuilder errbuf = new StringBuilder();
                    //open device
                    Pcap pcap = Pcap.openLive(selectedDevice.getName(), snaplen, flags, timeoutms, errbuf);
                    if (pcap == null) {
                        System.err.printf("Error while opening device for capture: "
                                + errbuf.toString());
                        return;
                    }

                    //capture packets
                    PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

                        int i = 0;
                        String dataPacket = "";
                        String time = "";
                        String source = "";
                        String destination = "";
                        String protocol = "";
                        String length = "";
                        String caplen = "";
                        String hlen = "";
                        String version = "";
                        String output = "0";

                        public void nextPacket(PcapPacket packet, String user) {
                            ++i;
                            //break loop when timeout
                            double now = System.currentTimeMillis();
                            double elapsedtime = (now - start) / 1000.0;

                            System.out.println("elapsed time " + elapsedtime);

                            progressBar.setValue((int) elapsedtime);
                            Border borderR = BorderFactory.createTitledBorder("Reading...");
                            progressBar.setBorder(borderR);

                            if (elapsedtime >= timeoutsec) {
                                progressBar.setValue(100);
                                Border border = BorderFactory.createTitledBorder("Done...");
                                progressBar.setBorder(border);

                                System.out.println("breaked");
                                pcap.breakloop();
                            }

                            //capturing fields
                            time = packet.getCaptureHeader().timestampInMillis() + "";
                            length = packet.getCaptureHeader().wirelen() + "";
                            caplen = packet.getCaptureHeader().caplen() + "";

                            //ip packet
                            Ip4 ip = new Ip4();
                            if (packet.hasHeader(ip) == true) {
                                source = FormatUtils.ip(ip.source());
                                destination = FormatUtils.ip(ip.destination());
                                protocol = "IP";
                                hlen = ip.hlen() + "";
                                version = ip.version() + "";
                            }

                            Tcp tcp = new Tcp();
                            if (packet.hasHeader(tcp) == true) {
                                source = tcp.source() + "";
                                destination = tcp.destination() + "";
                                protocol = "Tcp";
                                hlen = tcp.hlen() + "";
                                version = "0";
                            }

                            Udp udp = new Udp();
                            if (packet.hasHeader(udp) == true) {
                                source = udp.source() + "";
                                destination = udp.destination() + "";
                                protocol = "Udp";
                                hlen = udp.getHeaderLength() + "";
                                version = "0";
                            }

                            Arp arp = new Arp();
                            if (packet.hasHeader(arp) == true) {
                                source = "0.0.0.0";
                                destination = "0.0.0.0";
                                protocol = "Arp";
                                hlen = arp.hlen() + "";
                                version = "0";
                            }

                            Icmp icmp = new Icmp();
                            if (packet.hasHeader(icmp) == true) {
                                source = "0.0.0.0";
                                destination = "0.0.0.0";
                                protocol = "Icmp";
                                hlen = icmp.getHeaderLength() + "";
                                version = "0";
                            }

                            dataPacket = time + "," + source + "," + destination + ","
                                    + protocol + "," + length + "," + caplen + ","
                                    + hlen + "," + version + "," + output + "\n";

                            Object[] ob = {i, time, source, destination, protocol, length, caplen, hlen, version};
                            dtmPacketTable.addRow(ob);

                            System.out.println(dataPacket);

                            try {
                                fileWriter.write(dataPacket);
                                fileWriter.flush();
                            } catch (IOException ex) {
                                Logger.getLogger(TrafficAnalyserPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    };

                    pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, "jNetPcap");

                    pcap.close();

                    fileWriter.close();

                    System.out.println("Device closed");

                } catch (IOException ex) {
                    Logger.getLogger(TrafficAnalyserPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }//GEN-LAST:event_startButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser jfcOpen = new JFileChooser();
        jfcOpen.showOpenDialog(null);
        File f = jfcOpen.getSelectedFile();
        locationText.setText(f.getAbsoluteFile() + "");
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel deviceLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField locationText;
    private javax.swing.JTable packetsDisplayTable;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton startButton;
    private javax.swing.JTextField timeText;
    // End of variables declaration//GEN-END:variables

}
