/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.sun.org.apache.xalan.internal.xsltc.dom.CurrentNodeListFilter;
import communication.Communication;
import constant.Constants;
import coordinator.MainCoordinator;
import domain.BudgetItem;
import domain.MonthlyBudget;
import domain.Signal;
import domain.User;
import frm.FrmViewMonthlyBudget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ModelTblBudgetItems;

/**
 *
 * @author Mia Pecelj
 */
public class ViewMonthlyBudgetController {

    FrmViewMonthlyBudget frmViewMonthlyBudget;
    ArrayList<BudgetItem> addBudgetItems=new ArrayList<>();
    ArrayList<BudgetItem> deleteBudgetItems=new ArrayList<>();
    ArrayList<BudgetItem> editBudgetItems=new ArrayList<>();
    
    public ViewMonthlyBudgetController(FrmViewMonthlyBudget frmViewMonthlyBudget) {
        this.frmViewMonthlyBudget = frmViewMonthlyBudget;
        addActionListener();
    }

    public void openForm() throws Exception {
        frmViewMonthlyBudget.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        fillForm();
        frmViewMonthlyBudget.setVisible(true);

    }

    public FrmViewMonthlyBudget getFrmViewMonthlyBudget() {
        return frmViewMonthlyBudget;
    }

    private void addActionListener() {
        frmViewMonthlyBudget.getShowActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ((ModelTblBudgetItems) frmViewMonthlyBudget.getTblBudgetItems().getModel()).clear();
                    MonthlyBudget budget = getBudget();
                    if (budget == null) {
                        JOptionPane.showMessageDialog(frmViewMonthlyBudget, "Monthly budget not defined for this date");
                        return;
                    }else{
                        JOptionPane.showMessageDialog(frmViewMonthlyBudget, "Monthly budget found");
                    }
                    MainCoordinator.getInstance().addParam(Constants.CURRENT_MONTHLY_BUDGET, budget);
                    ArrayList<BudgetItem> budgetItems = Communication.getInstance().getAllBudgetItems();
                    System.out.println("BUDGET ITEMS" + budgetItems);
                    if (budgetItems.isEmpty()) {
                        JOptionPane.showMessageDialog(frmViewMonthlyBudget, "Budget items not defined for that month and year");

                    } else {
                        for (BudgetItem bi : budgetItems) {
                            if (bi.getBudget().getBudgetID() == budget.getBudgetID()) {
                                System.out.println(bi.getBudgetItemID());
                                ((ModelTblBudgetItems) frmViewMonthlyBudget.getTblBudgetItems().getModel()).add(bi);
                            }
                        }
                    }
                    frmViewMonthlyBudget.getLimit().setText(budget.getLimit().toString());
                    frmViewMonthlyBudget.getCurrState().setText(budget.getCurrentState().toString());
                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska fill monthly budget");
                }
            }
        }
        );
        frmViewMonthlyBudget.getAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MonthlyBudget budget = getBudget();
                    if (budget == null) {
                        JOptionPane.showMessageDialog(frmViewMonthlyBudget, "monthly budget not defined for this date");
                        return;
                    }
                    MainCoordinator.getInstance().addParam(Constants.CURRENT_MONTHLY_BUDGET, budget);
                    MainCoordinator.getInstance().openBudgetItemForm("view");
                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska add monthly budget");
                }
            }
        }
        );
        frmViewMonthlyBudget.getEditBudgetItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MonthlyBudget budget = getBudget();
                    if (budget == null) {
                        JOptionPane.showMessageDialog(frmViewMonthlyBudget, "monthly budget not defined for this date");
                        return;
                    }
                    MainCoordinator.getInstance().addParam(Constants.CURRENT_MONTHLY_BUDGET, budget);
                    int selectedRow = frmViewMonthlyBudget.getTblBudgetItems().getSelectedRow();
                    BudgetItem bi = ((ModelTblBudgetItems) frmViewMonthlyBudget.getTblBudgetItems().getModel()).getBudgetItemAt(selectedRow);
                    MainCoordinator.getInstance().addParam(Constants.CURRENT_BUDGET_ITEM, bi);
                    //System.out.println(bi.getBudgetItemID());
                    MainCoordinator.getInstance().openBudgetItemForm("edit");
                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska add monthly budget");
                }
            }
        }
        );
        frmViewMonthlyBudget.addBtnRemoveItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removeBudgetItem();
                } catch (Exception ex) {
                    Logger.getLogger(ViewMonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void removeBudgetItem() throws Exception {
                int rowIndex = frmViewMonthlyBudget.getTblBudgetItems().getSelectedRow();
                BudgetItem bi = ((ModelTblBudgetItems) frmViewMonthlyBudget.getTblBudgetItems().getModel()).getBudgetItemAt(rowIndex);
                deleteBudgetItems.add(bi);
                if (rowIndex >= 0) {
                    ((ModelTblBudgetItems) frmViewMonthlyBudget.getTblBudgetItems().getModel()).remove(rowIndex);
                } else {
                    JOptionPane.showMessageDialog(frmViewMonthlyBudget, "BudegetItem item is not selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmViewMonthlyBudget.addBtnSaveItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MonthlyBudget budget = getBudget();
                    if (budget == null) {
                        JOptionPane.showMessageDialog(frmViewMonthlyBudget, "monthly budget not defined for this date");
                        return;
                    }
                    String limitS = frmViewMonthlyBudget.getLimit().getText();
                    if (limitS.isEmpty()) {
                        JOptionPane.showMessageDialog(frmViewMonthlyBudget, "Enter a new limit");
                        return;
                    }
                    double limit = Double.parseDouble(limitS);
                    double sum = 0;
                    System.out.println(budget.getBudgetItems().size());
                    for (BudgetItem bi : budget.getBudgetItems()) {
                        if(bi.getBudget().getBudgetID()==budget.getBudgetID())
                        sum += bi.getLimit();

                    }
                    if (sum > limit) {
                        JOptionPane.showMessageDialog(frmViewMonthlyBudget, "this limit is less than the sum of budget items");
                        frmViewMonthlyBudget.getLimit().setText(budget.getLimit().toString());
                        return;
                    }
                    if (budget.getCurrentState() > limit) {
                        JOptionPane.showMessageDialog(frmViewMonthlyBudget, "Limit can not be set. Limit is less than current state");
                        frmViewMonthlyBudget.getLimit().setText(budget.getLimit().toString());
                        return;
                    }
                    ModelTblBudgetItems model = ((ModelTblBudgetItems)frmViewMonthlyBudget.getTblBudgetItems().getModel());
                    budget.setLimit(limit);
                    budget.setBudgetItems(model.getBudgetItems());
                    switch(JOptionPane.showConfirmDialog(frmViewMonthlyBudget, "Are you sure?")){
                        case 2:
                            return;
                        case 1:
                            return;
                        case 0:
                            ArrayList<BudgetItem> saveBudgetItems = new ArrayList<>();
                            for(BudgetItem bi: editBudgetItems){
                                bi.setSigna(Signal.Edit);
                                saveBudgetItems.add(bi);
                            }
                            for(BudgetItem bi: addBudgetItems){
                                bi.setSigna(Signal.Add);
                                saveBudgetItems.add(bi);
                            }
                            for(BudgetItem bi: deleteBudgetItems){
                                bi.setSigna(Signal.Delete);
                                saveBudgetItems.add(bi);
                            }
                            budget.setBudgetItems(saveBudgetItems);
                            Communication.getInstance().editMonthlyBudgetItems(budget);
                            JOptionPane.showMessageDialog(frmViewMonthlyBudget, "Budget edited");
                            frmViewMonthlyBudget.dispose();
                    }
                    //Communication.getInstance().editMonthlyBudget(budget);
                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska add monthly budget");
                }
            }
        }
        );
        frmViewMonthlyBudget.getSearchtEventListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                ModelTblBudgetItems model = new ModelTblBudgetItems(new ArrayList<>());
                frmViewMonthlyBudget.getTblBudgetItems().setModel(model);
                User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                ArrayList<MonthlyBudget> monthlyBudgets = new ArrayList<>();
                MonthlyBudget budget = new MonthlyBudget();
                try {
                    monthlyBudgets = Communication.getInstance().getAllMonthlyBudgets();

                    for (MonthlyBudget mb : monthlyBudgets) {
                        if (mb.getUser().getUserID() == user.getUserID() && mb.getMonth() == (int) frmViewMonthlyBudget.getCbMonth().getSelectedItem() && mb.getYear() == (int) frmViewMonthlyBudget.getCbYear().getSelectedItem()) {
                            budget = mb;
                        }
                    }

                    ArrayList<BudgetItem> budgetItems = Communication.getInstance().getAllBudgetItems();
                    for (BudgetItem bi : budgetItems) {
                        if (bi.getBudget().getBudgetID() == budget.getBudgetID() && bi.getCategory().getName().toLowerCase().contains(frmViewMonthlyBudget.getInputSearch().getText().toLowerCase())) {
                            model.add(bi);
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ViewMonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        );

    }

    private void fillForm() throws Exception {
        for (int i = 1; i <= 12; i++) {
            frmViewMonthlyBudget.getCbMonth().addItem(i);
        }
        for (int i = 2021; i <= 2030; i++) {
            frmViewMonthlyBudget.getCbYear().addItem(i);
        }
        ArrayList<BudgetItem> budgetItems = new ArrayList<>();
        ModelTblBudgetItems mtb = new ModelTblBudgetItems(budgetItems);
        frmViewMonthlyBudget.getTblBudgetItems().setModel(mtb);

    }

    public void addBudgetItem(BudgetItem budgetItem) throws Exception {
        MonthlyBudget budget = getBudget();
        if (budget == null) {
            JOptionPane.showMessageDialog(frmViewMonthlyBudget, "monthly budget not defined for this date");
            return;
        }
        budgetItem.setBudget(budget);
        //Communication.getInstance().addBudgetItem(budgetItem);
        ((ModelTblBudgetItems) frmViewMonthlyBudget.getTblBudgetItems().getModel()).add(budgetItem);
        addBudgetItems.add(budgetItem);
    }

    public void editBudgetItem(BudgetItem budgetItem) throws Exception {
        try {
            //((ModelTblBudgetItems) frmViewMonthlyBudget.getTblBudgetItems().getModel()).clear();
            MonthlyBudget budget = getBudget();
            if (budget == null) {
                JOptionPane.showMessageDialog(frmViewMonthlyBudget, "monthly budget not defined for this date");
                return;
            }
            MainCoordinator.getInstance().addParam(Constants.CURRENT_MONTHLY_BUDGET, budget);
            //ArrayList<BudgetItem> budgetItems = Communication.getInstance().getAllBudgetItems();
            ModelTblBudgetItems model = ((ModelTblBudgetItems)frmViewMonthlyBudget.getTblBudgetItems().getModel());
             ArrayList<BudgetItem> budgetItems = model.getBudgetItems();
            for (BudgetItem bi : budgetItems) {
                if (bi.getCategory()==budgetItem.getCategory() ) {
                    System.out.println(bi);
                    ((ModelTblBudgetItems) frmViewMonthlyBudget.getTblBudgetItems().getModel()).edit(bi,bi.getLimit(),bi.getCurrentState());
                    editBudgetItems.add(bi);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("greska fill monthly budget");
        }
    }

    public MonthlyBudget getBudget() throws Exception {
        int month = (int) frmViewMonthlyBudget.getCbMonth().getSelectedItem();
        int year = (int) frmViewMonthlyBudget.getCbYear().getSelectedItem();
        User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        ArrayList<MonthlyBudget> mb = Communication.getInstance().getAllMonthlyBudgets();
        MonthlyBudget budget = new MonthlyBudget();
        for (MonthlyBudget m : mb) {
            if (m.getMonth() == month && m.getYear() == year && m.getUser().getUserID() == user.getUserID()) {
                budget = m;
                System.out.println("BUDGETID" + budget.getBudgetID());
            }
        }
        System.out.println(budget.getBudgetItems().size());
        if (budget.getUser() == null) {
            return null;
        }
        return budget;

    }
}
