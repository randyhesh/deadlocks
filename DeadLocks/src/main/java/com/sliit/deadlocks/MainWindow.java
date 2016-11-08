/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sliit.deadlocks;

import com.sliit.views.Home;
import com.sliit.views.MainHome;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Heshani
 */
public class MainWindow {

    public static void main(String[] args) {
        String[] li = {"Licensee=Alvaro Duran Tovar  ", "LicenseRegistrationNumber=------", "Product=Synthetica", "LicenseType=Non Commercial", "ExpireDate=--.--.----", "MaxVersion=2.999.999"};
        UIManager.put("Synthetica.license.info", li);
        UIManager.put("Synthetica.license.key", "7C970FF4-E59D6AF5-8376C987-6F82C092-8377BB97");

        try {
            try {
                UIManager.setLookAndFeel(new SyntheticaSkyMetallicLookAndFeel());
                MainHome home = new MainHome();
                home.setLocationRelativeTo(null);
                home.setTitle("DeadLocks - NN");
                home.pack();
                home.setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
