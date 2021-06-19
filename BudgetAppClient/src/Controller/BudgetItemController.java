/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import communication.Communication;
import constant.Constants;
import coordinator.MainCoordinator;
import domain.BudgetItem;
import domain.Category;
import domain.MonthlyBudget;
import frm.FrmBudgetItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;
import model.ModelTblBudgetItems;

/**
 *
 * @author Mia Pecelj
 */
public class BudgetItemController {

    FrmBudgetItem frmBudgetItem;
    String param;

    public BudgetItemController(FrmBudgetItem frmBudgetItem, String param) {
        this.frmBudgetItem = frmBudgetItem;
        this.param = param;
        addActionListener();
    }

    public void openForm() {
        frmBudgetItem.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        if (param.equals("edit")) {
            BudgetItem bi = (BudgetItem) MainCoordinator.getInstance().getParam(Constants.CURRENT_BUDGET_ITEM);
            frmBudgetItem.getInputLimit().setText(String.valueOf(bi.getLimit()));
            frmBudgetItem.getCbCategory().setSelectedItem(bi.getCategory());
            frmBudgetItem.getCbCategory().setEnabled(false);
        }
        frmBudgetItem.setVisible(true);

    }

    private void addActionListener() {
        frmBudgetItem.SaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refreshFOrm();
                MonthlyBudget mb = (MonthlyBudget) MainCoordinator.getInstance().getParam(Constants.CURRENT_MONTHLY_BUDGET);
                BudgetItem budgetItem = new BudgetItem();
                if (param.equals("edit")) {
                    budgetItem = (BudgetItem) MainCoordinator.getInstance().getParam(Constants.CURRENT_BUDGET_ITEM);
                }
                budgetItem.setCategory((Category) frmBudgetItem.getCbCategory().getSelectedItem());
                String limitS = frmBudgetItem.getInputLimit().getText().trim();
                if (limitS.isEmpty()) {
                    frmBudgetItem.getLblErrorLimit().setText("Enter limit");
                    return;
                }
                ArrayList<BudgetItem> budgetItems;
                if (param.equals("create")) {
                    budgetItems = ((ModelTblBudgetItems) MainCoordinator.getInstance().getMonthlyBudgetController().getFrmMonthlyBudget().getTblBudgetItems().getModel()).getBudgetItems();
                } else {
                    budgetItems = ((ModelTblBudgetItems) MainCoordinator.getInstance().getViewMonthlyBudgetController().getFrmViewMonthlyBudget().getTblBudgetItems().getModel()).getBudgetItems();
                }
                double limitSum = 0;
                double limit = Double.parseDouble(limitS);
                if (limit < 0) {
                    frmBudgetItem.getLblErrorLimit().setText("Limit must be positive");
                    return;
                }
                for (BudgetItem bi : budgetItems) {
                    if (budgetItem.getCategory().equals(bi.getCategory()) && !param.equals("edit")) {
                        JOptionPane.showMessageDialog(frmBudgetItem, "Category exist");
                        frmBudgetItem.dispose();
                        return;
                    }
                }
                 for (BudgetItem bi : budgetItems) {
                     limitSum += bi.getLimit();
                }
                                   
                if (limitS.isEmpty()) {
                    frmBudgetItem.getLblErrorLimit().setText("Enter limit");
                    return;
                }

                budgetItem.setLimit(limit);
                if (param.equals("edit")) {
                    if (budgetItem.getLimit() < budgetItem.getCurrentState()) {
                        budgetItem.setCurrentState(Double.parseDouble(limitS));
                    }
                } else {
                    budgetItem.setCurrentState(Double.parseDouble(limitS));
                }

                budgetItem.setBudget(mb);
                System.out.println(limitSum);
                if (limitSum+budgetItem.getLimit() <= mb.getLimit()) {
                    try {
                        if (!param.equals("edit")) {
                            MainCoordinator.getInstance().addBudgetItem(budgetItem, param);
                        } else {
                            MainCoordinator.getInstance().editBudgetItem(budgetItem);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(BudgetItemController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frmBudgetItem.dispose();
                } else {
                    frmBudgetItem.getLblErrorLimit().setText("Budget item is surpassing limit");
                }

            }

        });
        frmBudgetItem.CancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frmBudgetItem.dispose();

            }

        });
    }

    private void prepareView() {
        try {
            ArrayList<Category> categories = Communication.getInstance().getAllCategories();
            for (Category c : categories) {
                frmBudgetItem.getCbCategory().addItem(c);
            }
        } catch (Exception ex) {
            Logger.getLogger(BudgetItemController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("prepare view budgetitem failed");
        }
    }

    public void refreshFOrm() {
        frmBudgetItem.getLblErrorLimit().setText("");
    }

}
