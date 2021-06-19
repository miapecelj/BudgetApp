/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import communication.Communication;
import constant.Constants;
import coordinator.MainCoordinator;
import domain.BudgetItem;
import domain.Expense;
import domain.MonthlyBudget;
import domain.User;
import frm.FrmExpense;
import frm.FrmViewExpenses;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ModelTblExpense;

/**
 *
 * @author Mia Pecelj
 */
public class ViewExpensesController {

    FrmViewExpenses frmViewExpenses;

    public ViewExpensesController(FrmViewExpenses frmViewExpenses) {
        this.frmViewExpenses = frmViewExpenses;
        addActionListener();
    }

    public void openForm() throws Exception {
         frmViewExpenses.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        fillTbl();
        frmViewExpenses.setVisible(true);
    }

    private void fillTbl() throws Exception {
        for (int i = 1; i <= 12; i++) {
            frmViewExpenses.getCbMonth().addItem(i);
        }
        for (int i = 1; i <= 31; i++) {
            frmViewExpenses.getCbDay().addItem(i);
        }
        for (int i = 2021; i <= 2030; i++) {
            frmViewExpenses.getCbYear().addItem(i);
        }
        ArrayList<Expense> expenses = Communication.getInstance().getAllExpenses();
        ArrayList<Expense> userExpenses = new ArrayList<>();
        User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        for (Expense e : expenses) {
            if (e.getBudget().getUser().getUserID() == user.getUserID()) {
                userExpenses.add(e);
            }
        }
        ModelTblExpense model = new ModelTblExpense(userExpenses);
        frmViewExpenses.getTblExpenses().setModel(model);
    }

    private void addActionListener() {
        frmViewExpenses.getFilterActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((ModelTblExpense) frmViewExpenses.getTblExpenses().getModel()).setExpenses(new ArrayList<>());
                try {
                    ArrayList<Expense> expenses = Communication.getInstance().getAllExpenses();
                    ArrayList<Expense> userExpenses = new ArrayList<>();

                    for (Expense ex : expenses) {
                        if (ex.getBudget().getUser().getUserID() == ((User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER)).getUserID()) {
                            userExpenses.add(ex);
                        }
                    }

                    if (frmViewExpenses.getCheckYear().isSelected()) {
                        int yearE = (int) frmViewExpenses.getCbYear().getSelectedItem();
                        int monthE = (int) frmViewExpenses.getCbMonth().getSelectedItem();
                        int dayE = (int) frmViewExpenses.getCbDay().getSelectedItem();
                        for (Expense expense : userExpenses) {
                            java.util.Date Date = new java.util.Date(expense.getDate().getTime());
                            LocalDate localDate = Date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            int year = localDate.getYear();
                            int month = localDate.getMonthValue();
                            int day = localDate.getDayOfMonth();
                            if (frmViewExpenses.getInputSearch().getText().isEmpty()) {
                                if (frmViewExpenses.getCheckYear().isSelected()) {
                                    if (frmViewExpenses.getCheckMonth().isSelected()) {
                                        if (frmViewExpenses.getCheckDay().isSelected()) {
                                            if (yearE == year && monthE == month && dayE == day) {
                                                ((ModelTblExpense) frmViewExpenses.getTblExpenses().getModel()).add(expense);
                                            }
                                        } else {
                                            if (yearE == year && monthE == month) {
                                                ((ModelTblExpense) frmViewExpenses.getTblExpenses().getModel()).add(expense);
                                            }
                                        }
                                    } else {
                                        if (yearE == year) {
                                            ((ModelTblExpense) frmViewExpenses.getTblExpenses().getModel()).add(expense);
                                        }
                                    }
                                }
                            }else if(expense.getName().toLowerCase().contains(frmViewExpenses.getInputSearch().getText().toLowerCase())){
                                if (frmViewExpenses.getCheckYear().isSelected()) {
                                    if (frmViewExpenses.getCheckMonth().isSelected()) {
                                        if (frmViewExpenses.getCheckDay().isSelected()) {
                                            if (yearE == year && monthE == month && dayE == day) {
                                                ((ModelTblExpense) frmViewExpenses.getTblExpenses().getModel()).add(expense);
                                            }
                                        } else {
                                            if (yearE == year && monthE == month) {
                                                ((ModelTblExpense) frmViewExpenses.getTblExpenses().getModel()).add(expense);
                                            }
                                        }
                                    } else {
                                        if (yearE == year) {
                                            ((ModelTblExpense) frmViewExpenses.getTblExpenses().getModel()).add(expense);
                                        }
                                    }
                                }
                            }
                        }
                        if(((ModelTblExpense) frmViewExpenses.getTblExpenses().getModel()).getExpenses().isEmpty()){
                        JOptionPane.showMessageDialog(frmViewExpenses, "There are no expenses for those parametars");
                    }
                    } else {
         
                        ((ModelTblExpense) frmViewExpenses.getTblExpenses().getModel()).setExpenses(userExpenses);
                    }
                    
                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska expense");
                }
            }
        }
        );
        frmViewExpenses.getRemoveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    ModelTblExpense model = (ModelTblExpense) frmViewExpenses.getTblExpenses().getModel();
                    int row = frmViewExpenses.getTblExpenses().getSelectedRow();
                    Expense expense = model.getExpenseAt(row);
                    model.remove(row);
                    System.out.println(expense.getExpenseID());
                    Communication.getInstance().deleteExpense(expense);
                    JOptionPane.showMessageDialog(frmViewExpenses, "Expense deleted");
                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska fill monthly budget");
                }
            }
        }
        );

        frmViewExpenses.getAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainCoordinator.getInstance().openExpenseForm();
                    fillTbl();
                    frmViewExpenses.getCheckDay().setSelected(false);
                    frmViewExpenses.getCheckMonth().setSelected(false);
                    frmViewExpenses.getCheckYear().setSelected(false);

                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska fill monthly budget");
                }
            }
        }
        );

        frmViewExpenses.getSearchtEventListener(new KeyListener() {
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
                ModelTblExpense model = new ModelTblExpense(new ArrayList<>());
                frmViewExpenses.getTblExpenses().setModel(model);
                User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                ArrayList<MonthlyBudget> monthlyBudgets = new ArrayList<>();
                try {
                    monthlyBudgets = Communication.getInstance().getAllMonthlyBudgets();
                    ArrayList<MonthlyBudget> monthlyBudgetsUser = new ArrayList<>();
                    for(MonthlyBudget mb: monthlyBudgets){
                        if(mb.getUser().getUserID()==user.getUserID()){
                            monthlyBudgetsUser.add(mb);
                        }
                    }
                    ArrayList<Expense> expenses = Communication.getInstance().getAllExpenses();
                    for (Expense ex : expenses) {
                        if (monthlyBudgetsUser.contains(ex.getBudget()) && ex.getName().toLowerCase().contains(frmViewExpenses.getInputSearch().getText().toLowerCase())) {
                            java.util.Date Date = new java.util.Date(ex.getDate().getTime());
                            LocalDate localDate = Date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            int year = localDate.getYear();
                            int month = localDate.getMonthValue();
                            int day = localDate.getDayOfMonth();
                            if (frmViewExpenses.getCheckYear().isSelected()) {
                                if (year != (int) frmViewExpenses.getCbYear().getSelectedItem()) {
                                    continue;
                                }
                                if (frmViewExpenses.getCheckMonth().isSelected()) {
                                    if (month != (int) frmViewExpenses.getCbMonth().getSelectedItem()) {
                                        continue;
                                    }
                                    if (frmViewExpenses.getCheckDay().isSelected()) {
                                        if (day != (int) frmViewExpenses.getCbDay().getSelectedItem()) {
                                            continue;
                                        }

                                        model.add(ex);
                                        continue;
                                    }
                                    model.add(ex);
                                    continue;
                                }
                                model.add(ex);
                                continue;
                            }
                            model.add(ex);
                        }

                    }
                } catch (Exception ex) {
                    Logger.getLogger(ViewMonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        );
    }

}
