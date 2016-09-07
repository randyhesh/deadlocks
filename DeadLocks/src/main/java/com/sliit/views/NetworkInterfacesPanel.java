/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

/**
 *
 * @author Heshani
 */
public class NetworkInterfacesPanel extends javax.swing.JPanel {

    private List<PcapIf> networkInterfacesList;
    JToggleButton buttnList[];
    JLabel descList[];
    JLabel datalinkList[];
    JLabel datalinkDescList[];
    JPanel panelList[];

    /**
     * Creates new form networkInterfacesList
     */
    public NetworkInterfacesPanel() {
        initComponents();

        buttnList = new JToggleButton[6];
        descList = new JLabel[6];
        datalinkList = new JLabel[6];
        datalinkDescList = new JLabel[6];
        panelList = new JPanel[6];

        buttonGroup1.add(device1);
        buttonGroup1.add(device2);
        buttonGroup1.add(device3);
        buttonGroup1.add(device4);
        buttonGroup1.add(device5);
        buttonGroup1.add(device6);

        buttnList[0] = device1;
        buttnList[1] = device2;
        buttnList[2] = device3;
        buttnList[3] = device4;
        buttnList[4] = device5;
        buttnList[5] = device6;

        descList[0] = descDev1;
        descList[1] = descDev2;
        descList[2] = descDev3;
        descList[3] = descDev4;
        descList[4] = descDev5;
        descList[5] = descDev6;

        panelList[0] = panelDev1;
        panelList[1] = panelDev2;
        panelList[2] = panelDev3;
        panelList[3] = panelDev4;
        panelList[4] = panelDev5;
        panelList[5] = panelDev6;

        getNetworkInterfaces();
    }

    private void getNetworkInterfaces() {

        //get all NICs
        List<PcapIf> devices = new ArrayList<PcapIf>();
        //for any error msg
        StringBuilder errbuf = new StringBuilder();
        int r = Pcap.findAllDevs(devices, errbuf);
        if (r == Pcap.NOT_OK || devices.isEmpty()) {
            System.out.println("Can't read list of devices, error is " + errbuf.toString());
            return;
        }

        this.networkInterfacesList = devices;

        int index = 0;
        for (PcapIf device : devices) {

            buttnList[index].setText(device.getName());
            descList[index].setText(device.getDescription());

            //add action listener
            DeviceListener deviceListener = new DeviceListener();
            buttnList[index].addActionListener(deviceListener);

            index++;
        }

        for (int i = index; i < panelList.length; i++) {
            System.out.println(panelList[i]);
            this.remove(panelList[i]);
            this.revalidate();
            this.repaint();

        }
    }

    class DeviceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Home.containerPanel.removeAll();
            Home.containerPanel.add(new TrafficAnalyserPanel(networkInterfacesList, e.getActionCommand()), "trafficAnalyserPanel", 0);
            Home.containerPanel.revalidate();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel6 = new javax.swing.JLabel();
        panelDev1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        device1 = new javax.swing.JToggleButton();
        descDev1 = new javax.swing.JLabel();
        panelDev4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        device4 = new javax.swing.JToggleButton();
        descDev4 = new javax.swing.JLabel();
        panelDev2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        device2 = new javax.swing.JToggleButton();
        descDev2 = new javax.swing.JLabel();
        panelDev3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        device3 = new javax.swing.JToggleButton();
        descDev3 = new javax.swing.JLabel();
        panelDev6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        device6 = new javax.swing.JToggleButton();
        descDev6 = new javax.swing.JLabel();
        panelDev5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        device5 = new javax.swing.JToggleButton();
        descDev5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1061, 480));
        setMinimumSize(new java.awt.Dimension(1061, 480));
        setPreferredSize(new java.awt.Dimension(905, 509));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 255));
        jLabel6.setText("NETWORK INTERFACES");

        panelDev1.setBackground(new java.awt.Color(0, 153, 255));
        panelDev1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDev1.setMaximumSize(new java.awt.Dimension(330, 178));
        panelDev1.setMinimumSize(new java.awt.Dimension(330, 178));

        jLabel5.setIcon(new javax.swing.ImageIcon("D:\\deadlocks\\DeadLocks\\src\\main\\java\\com\\sliit\\images\\Industry-Rfid-Signal-icon.png")); // NOI18N

        device1.setBackground(new java.awt.Color(0, 153, 255));
        device1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        device1.setText("jToggleButton1");

        descDev1.setText("jLabel1");

        javax.swing.GroupLayout panelDev1Layout = new javax.swing.GroupLayout(panelDev1);
        panelDev1.setLayout(panelDev1Layout);
        panelDev1Layout.setHorizontalGroup(
            panelDev1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(device1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panelDev1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(descDev1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDev1Layout.setVerticalGroup(
            panelDev1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(device1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelDev1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(descDev1)))
        );

        panelDev4.setBackground(new java.awt.Color(255, 0, 204));
        panelDev4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setIcon(new javax.swing.ImageIcon("D:\\deadlocks\\DeadLocks\\src\\main\\java\\com\\sliit\\images\\Industry-Rfid-Signal-icon.png")); // NOI18N

        device4.setBackground(new java.awt.Color(255, 0, 204));
        device4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        device4.setText("jToggleButton1");

        descDev4.setText("jLabel1");

        javax.swing.GroupLayout panelDev4Layout = new javax.swing.GroupLayout(panelDev4);
        panelDev4.setLayout(panelDev4Layout);
        panelDev4Layout.setHorizontalGroup(
            panelDev4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(device4, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panelDev4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(descDev4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDev4Layout.setVerticalGroup(
            panelDev4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(device4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(panelDev4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(descDev4)))
        );

        panelDev2.setBackground(new java.awt.Color(51, 255, 0));
        panelDev2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDev2.setMaximumSize(new java.awt.Dimension(340, 178));
        panelDev2.setMinimumSize(new java.awt.Dimension(340, 178));

        jLabel7.setIcon(new javax.swing.ImageIcon("D:\\deadlocks\\DeadLocks\\src\\main\\java\\com\\sliit\\images\\Industry-Rfid-Signal-icon.png")); // NOI18N

        device2.setBackground(new java.awt.Color(51, 255, 0));
        device2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        device2.setText("jToggleButton1");

        descDev2.setText("jLabel1");

        javax.swing.GroupLayout panelDev2Layout = new javax.swing.GroupLayout(panelDev2);
        panelDev2.setLayout(panelDev2Layout);
        panelDev2Layout.setHorizontalGroup(
            panelDev2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(device2, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panelDev2Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(descDev2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDev2Layout.setVerticalGroup(
            panelDev2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(device2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(panelDev2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(descDev2)))
        );

        panelDev3.setBackground(new java.awt.Color(153, 0, 153));
        panelDev3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDev3.setMaximumSize(new java.awt.Dimension(330, 182));
        panelDev3.setMinimumSize(new java.awt.Dimension(330, 182));

        jLabel10.setIcon(new javax.swing.ImageIcon("D:\\deadlocks\\DeadLocks\\src\\main\\java\\com\\sliit\\images\\Industry-Rfid-Signal-icon.png")); // NOI18N

        device3.setBackground(new java.awt.Color(153, 0, 153));
        device3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        device3.setText("jToggleButton1");

        descDev3.setText("jLabel1");

        javax.swing.GroupLayout panelDev3Layout = new javax.swing.GroupLayout(panelDev3);
        panelDev3.setLayout(panelDev3Layout);
        panelDev3Layout.setHorizontalGroup(
            panelDev3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(device3, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelDev3Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(descDev3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDev3Layout.setVerticalGroup(
            panelDev3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(device3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(panelDev3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(descDev3)))
        );

        panelDev6.setBackground(new java.awt.Color(0, 255, 204));
        panelDev6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDev6.setMaximumSize(new java.awt.Dimension(330, 178));
        panelDev6.setMinimumSize(new java.awt.Dimension(330, 178));

        jLabel11.setBackground(new java.awt.Color(0, 255, 204));
        jLabel11.setIcon(new javax.swing.ImageIcon("D:\\deadlocks\\DeadLocks\\src\\main\\java\\com\\sliit\\images\\Industry-Rfid-Signal-icon.png")); // NOI18N

        device6.setBackground(new java.awt.Color(0, 255, 204));
        device6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        device6.setText("jToggleButton1");

        descDev6.setText("jLabel1");

        javax.swing.GroupLayout panelDev6Layout = new javax.swing.GroupLayout(panelDev6);
        panelDev6.setLayout(panelDev6Layout);
        panelDev6Layout.setHorizontalGroup(
            panelDev6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(device6, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelDev6Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(descDev6, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDev6Layout.setVerticalGroup(
            panelDev6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(device6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(panelDev6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(descDev6)))
        );

        panelDev5.setBackground(new java.awt.Color(255, 102, 0));
        panelDev5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setIcon(new javax.swing.ImageIcon("D:\\deadlocks\\DeadLocks\\src\\main\\java\\com\\sliit\\images\\Industry-Rfid-Signal-icon.png")); // NOI18N

        device5.setBackground(new java.awt.Color(255, 102, 0));
        device5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        device5.setText("jToggleButton1");

        descDev5.setText("jLabel1");

        javax.swing.GroupLayout panelDev5Layout = new javax.swing.GroupLayout(panelDev5);
        panelDev5.setLayout(panelDev5Layout);
        panelDev5Layout.setHorizontalGroup(
            panelDev5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(device5, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelDev5Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(descDev5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDev5Layout.setVerticalGroup(
            panelDev5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(device5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelDev5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(descDev5)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelDev1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panelDev2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(panelDev4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panelDev5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelDev3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelDev6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelDev3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelDev1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDev2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(panelDev5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelDev4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelDev6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel descDev1;
    private javax.swing.JLabel descDev2;
    private javax.swing.JLabel descDev3;
    private javax.swing.JLabel descDev4;
    private javax.swing.JLabel descDev5;
    private javax.swing.JLabel descDev6;
    private javax.swing.JToggleButton device1;
    private javax.swing.JToggleButton device2;
    private javax.swing.JToggleButton device3;
    private javax.swing.JToggleButton device4;
    private javax.swing.JToggleButton device5;
    private javax.swing.JToggleButton device6;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelDev1;
    private javax.swing.JPanel panelDev2;
    private javax.swing.JPanel panelDev3;
    private javax.swing.JPanel panelDev4;
    private javax.swing.JPanel panelDev5;
    private javax.swing.JPanel panelDev6;
    // End of variables declaration//GEN-END:variables
}
