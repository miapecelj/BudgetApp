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
import domain.MonthlyBudget;
import domain.User;
import frm.FrmMonthlyBudget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ModelTblBudgetItems;

/**
 *
 * @author Mia Pecelj
 */
public class MonthlyBudgetController {

    FrmMonthlyBudget frmMonthlyBudget;

    public FrmMonthlyBudget getFrmMonthlyBudget() {
        return frmMonthlyBudget;
    }

    public void setFrmMonthlyBudget(FrmMonthlyBudget frmMonthlyBudget) {
        this.frmMonthlyBudget = frmMonthlyBudget;
    }

    public MonthlyBudgetController(FrmMonthlyBudget frmMonthlyBudget) {
        this.frmMonthlyBudget = frmMonthlyBudget;
        addActionListener();
    }

    public void openForm() {
        frmMonthlyBudget.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        preparerView();
        fillTbl();
        frmMonthlyBudget.setVisible(true);
    }

    private void addActionListener() {
        frmMonthlyBudget.getJmiBudgetItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    resetForm();
                    int month = (int) frmMonthlyBudget.getCbMonth().getSelectedItem();
                    int year = (int) frmMonthlyBudget.getCbYear().getSelectedItem();
                    String limitS = frmMonthlyBudget.getLimit().getText().trim();
                    if (limitS.isEmpty()) {
                        frmMonthlyBudget.getLblErrorLimit().setText("Enter limit");
                        return;
                    }
                    double limit = Double.parseDouble(limitS);
                    if (limit < 0) {
                        frmMonthlyBudget.getLblErrorLimit().setText("limit must be positive");
                    }
                    MonthlyBudget mb = new MonthlyBudget();
                    mb.setCurrentState(limit);
                    mb.setLimit(limit);
                    mb.setMonth(month);
                    mb.setYear(year);
                    mb.setUser((User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
                    frmMonthlyBudget.getCbMonth().addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            changeBudgetItems(e);
                        }

                        private void changeBudgetItems(ItemEvent e) {
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                int newMonth = (int) e.getItem();
                                MonthlyBudget mb = new MonthlyBudget();
                                mb.setCurrentState(limit);
                                mb.setLimit(limit);
                                mb.setMonth(newMonth);
                                mb.setYear(year);
                                MainCoordinator.getInstance().addParam(Constants.CURRENT_MONTHLY_BUDGET, mb);
                                ArrayList<BudgetItem> budgetItems = ((ModelTblBudgetItems) frmMonthlyBudget.getTblBudgetItems().getModel()).getBudgetItems();
                                for (BudgetItem bi : budgetItems) {
                                    bi.setBudget(mb);
                                }

                            }
                        }
                    });
                    frmMonthlyBudget.getCbYear().addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            changeBudgetItems(e);
                        }

                        private void changeBudgetItems(ItemEvent e) {
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                int newYear = (int) e.getItem();
                                MonthlyBudget mb = new MonthlyBudget();
                                mb.setCurrentState(limit);
                                mb.setLimit(limit);
                                mb.setMonth(month);
                                mb.setYear(newYear);
                                MainCoordinator.getInstance().addParam(Constants.CURRENT_MONTHLY_BUDGET, mb);
                                ArrayList<BudgetItem> budgetItems = ((ModelTblBudgetItems) frmMonthlyBudget.getTblBudgetItems().getModel()).getBudgetItems();
                                for (BudgetItem bi : budgetItems) {
                                    bi.setBudget(mb);
                                }

                            }
                        }
                    });
                    MainCoordinator.getInstance().addParam(Constants.CURRENT_MONTHLY_BUDGET, mb);
                    MainCoordinator.getInstance().openBudgetItemForm("create");
                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska budget item");
                }
            }
        });
        frmMonthlyBudget.getCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmMonthlyBudget.dispose();
            }
        });
        frmMonthlyBudget.getSaveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resetForm();
                    int month = (int) frmMonthlyBudget.getCbMonth().getSelectedItem();
                    int year = (int) frmMonthlyBudget.getCbYear().getSelectedItem();
                    String limitS = frmMonthlyBudget.getLimit().getText().trim();
                    if (limitS.isEmpty()) {
                        frmMonthlyBudget.getLblErrorLimit().setText("Enter limit");
                        return;
                    }
                    double limit = Double.parseDouble(limitS);
                    if (limit < 0) {
                        frmMonthlyBudget.getLblErrorLimit().setText("limit must be positive");
                        return;
                    }
                    MonthlyBudget mb = new MonthlyBudget();
                    mb.setCurrentState(limit);
                    mb.setLimit(limit);
                    mb.setMonth(month);
                    mb.setYear(year);
                    mb.setUser((User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
                    int sumLim = 0;
                    ArrayList<BudgetItem> budgetItems = ((ModelTblBudgetItems) frmMonthlyBudget.getTblBudgetItems().getModel()).getBudgetItems();
                    for (BudgetItem bi : budgetItems) {
                        sumLim += bi.getLimit();
                        System.out.println("sumLim"+sumLim);
                    }
                    if (sumLim > limit) {
                        JOptionPane.showMessageDialog(frmMonthlyBudget, "limit for budget items are surpassing monthly limit");
                        return;
                    }
                    mb.setBudgetItems(budgetItems);
                    MainCoordinator.getInstance().addParam(Constants.CURRENT_MONTHLY_BUDGET, mb);
                    User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                    ArrayList<MonthlyBudget> budgets = Communication.getInstance().getAllMonthlyBudgets();
                    for (MonthlyBudget b : budgets) {
                        if (b.getMonth() == mb.getMonth() && b.getYear() == mb.getYear() && b.getUser().getUserID() == user.getUserID()) {
                            JOptionPane.showMessageDialog(frmMonthlyBudget, "Monthly budget already defined");
                            return;
                        }
                    }
                    Communication.getInstance().addMonhlyBudget(mb);
                    JOptionPane.showMessageDialog(frmMonthlyBudget, "Monthly budget saved!");
                    frmMonthlyBudget.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska budget item");
                }
                frmMonthlyBudget.dispose();
            }
        });
        frmMonthlyBudget.addBtnRemoveItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeInvoiceItem();
            }

            private void removeInvoiceItem() {
                int rowIndex = frmMonthlyBudget.getTblBudgetItems().getSelectedRow();
                ModelTblBudgetItems model = (ModelTblBudgetItems) frmMonthlyBudget.getTblBudgetItems().getModel();
                if (rowIndex >= 0) {
                    model.remove(rowIndex);
                } else {
                    JOptionPane.showMessageDialog(frmMonthlyBudget, "BudegetItem item is not selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmMonthlyBudget.addBtnCategoryItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openCategoryForm();
            }
        });
    }

    private void preparerView() {
        for (int i = 1; i <= 12; i++) {
            frmMonthlyBudget.getCbMonth().addItem(i);
        }
        for (int i = 2021; i <= 2050; i++) {
            frmMonthlyBudget.getCbYear().addItem(i);
        }
    }

    private void fillTbl() {
        ArrayList<BudgetItem> budgetItems = new ArrayList<>();
        ModelTblBudgetItems mtc = new ModelTblBudgetItems(budgetItems);
        frmMonthlyBudget.getTblBudgetItems().setModel(mtc);
    }

    public void addBudgetItem(BudgetItem budgetItem) {
        ((ModelTblBudgetItems) frmMonthlyBudget.getTblBudgetItems().getModel()).add(budgetItem);
    }

    public void editBudgetItem(BudgetItem budgetItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void resetForm() {
        frmMonthlyBudget.getLblErrorLimit().setText("");

    }
    

}
