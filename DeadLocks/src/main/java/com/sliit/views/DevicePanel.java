/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.views;

/**
 *
 * @author Heshani
 */
public class DevicePanel extends javax.swing.JPanel {

    /**
     * Creates new form DevicePanel
     */
    public DevicePanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDev1 = new javax.swing.JPanel();
        deviceNameBtn = new javax.swing.JToggleButton();
        descLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        panelDev1.setBackground(new java.awt.Color(255, 255, 255));
        panelDev1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        panelDev1.setMaximumSize(new java.awt.Dimension(330, 178));
        panelDev1.setMinimumSize(new java.awt.Dimension(330, 178));

        deviceNameBtn.setBackground(new java.awt.Color(255, 255, 255));
        deviceNameBtn.setText("jToggleButton1");
        deviceNameBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        deviceNameBtn.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        deviceNameBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        descLabel.setText("jLabel1");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cnnecteddev.png"))); // NOI18N

        javax.swing.GroupLayout panelDev1Layout = new javax.swing.GroupLayout(panelDev1);
        panelDev1.setLayout(panelDev1Layout);
        panelDev1Layout.setHorizontalGroup(
            panelDev1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDev1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deviceNameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDev1Layout.setVerticalGroup(
            panelDev1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDev1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDev1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(panelDev1Layout.createSequentialGroup()
                        .addComponent(deviceNameBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDev1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDev1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel descLabel;
    public javax.swing.JToggleButton deviceNameBtn;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JPanel panelDev1;
    // End of variables declaration//GEN-END:variables
}
