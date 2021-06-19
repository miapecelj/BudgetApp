/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import constant.Constants;
import coordinator.MainCoordinator;
import domain.BudgetItem;
import domain.User;
import frm.FrmMain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author laptop-02
 */
public class MainContoller {

    private final FrmMain frmMain;

    public MainContoller(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListener();
    }

    public void openForm() {
        User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        frmMain.getLblCurrentUser().setText(user.getFirstName() + ", " + user.getLastName());
        frmMain.setVisible(true);
    }

    private void addActionListener() {
        frmMain.getJmiCategoryAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openCategoryForm();
            }
        });
        frmMain.getJmiMonthlyBudgetAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openMonthlyBudgetForm();
            }
        });
        frmMain.getJmiMonthlyBudgetViewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCoordinator.getInstance().openViewMonthlyBudgetForm();
                } catch (Exception ex) {
                    Logger.getLogger(MainContoller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frmMain.getJmiExpenseAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCoordinator.getInstance().openExpenseForm();
                } catch (Exception ex) {
                    Logger.getLogger(MainContoller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frmMain.getJmiExpenseViewActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCoordinator.getInstance().openExpenseViewForm();
                } catch (Exception ex) {
                    Logger.getLogger(MainContoller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        frmMain.getLogOut(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCoordinator.getInstance().openLoginForm();
                    frmMain.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(MainContoller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public FrmMain getFrmMain() {
        return frmMain;
    }

    
}
