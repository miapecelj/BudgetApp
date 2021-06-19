/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import server.Server;
import view.frm.FrmMain;

/**
 *
 * @author Mia Pecelj
 */
public class MainController {
    private final FrmMain frmMain;
    private Server server;

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListeners();
    }

    public void openForm() {
        frmMain.setVisible(true);
    }

    private void addActionListeners() {
        frmMain.AddBtnStartActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server == null || (server != null && !server.isAlive())) {
                    server = new Server();
                    server.start();
                }
            }
        });
        
        frmMain.AddBtnStopActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server != null)
                    server.stopServer();
            }
        });
        frmMain.AddJmiSettingsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openSettingsForm();
            }
        });
    }

    public FrmMain getFrmMain() {
        return frmMain;
    }
    
}
