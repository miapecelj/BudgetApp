/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import java.util.HashMap;
import java.util.Map;
import view.frm.FrmMain;
import view.frm.FrmSettings;

/**
 *
 * @author EMA
 */
public class MainCoordinator {
    private static MainCoordinator instance;

    private final MainController mainController;

    public MainController getMainController() {
        return mainController;
    }
    

    private MainCoordinator() {
        mainController = new MainController(new FrmMain());
    }

    public static MainCoordinator getInstance() {
        if (instance == null) {
            instance = new MainCoordinator();
        }
        return instance;
    }
     public void openMainForm() {
        mainController.openForm();
    }
     
    public void openSettingsForm() {
       SettingsController settingsController = new SettingsController( new FrmSettings(mainController.getFrmMain(), true));
       settingsController.openForm();
    }

    
}
