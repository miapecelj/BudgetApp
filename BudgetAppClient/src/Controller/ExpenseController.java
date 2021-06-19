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
import domain.Expense;
import domain.MonthlyBudget;
import domain.User;
import frm.FrmExpense;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mia Pecelj
 */
public class ExpenseController {

    FrmExpense frmExpense;

    public ExpenseController(FrmExpense frmExpense) {
        this.frmExpense = frmExpense;
        addActionListener();
    }

    public void openForm() throws Exception {
        frmExpense.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        fillForm();
        frmExpense.setVisible(true);
    }

    private void fillForm() throws Exception {
        ArrayList<Category> categories;
        categories = Communication.getInstance().getAllCategories();
        for (Category c : categories) {
            frmExpense.getCbCategory().addItem(c);
        }
    }

    private void addActionListener() {

        frmExpense.getCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmExpense.dispose();
            }
        });
        frmExpense.getSaveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resetForm();
                    if(validate()){
                    Date date = frmExpense.getjDate().getDate();
                    String name = frmExpense.getInputName().getText();
                    String amountS = frmExpense.getInputAmount().getText();
                    Category cat = (Category) frmExpense.getCbCategory().getSelectedItem();
                    double amount = Double.parseDouble(amountS);
                    MonthlyBudget budget = getBudget();
                    if (budget.getLimit() == null) {
                        JOptionPane.showMessageDialog(frmExpense, "Budget not defined fot that month and year");
                        return;
                    }
                    if (amount < 0) {
                        frmExpense.getLblErrorAmount().setText("amout must be positive");
                        return;
                    }
                    Expense expense = new Expense(0, date, amount, name, cat, budget);
                    Communication.getInstance().addExpense(expense);
                    budget.setCurrentState(budget.getCurrentState() - amount);
                    Communication.getInstance().editMonthlyBudget(budget);
                    ArrayList<BudgetItem> budgetItems = (ArrayList<BudgetItem>) Communication.getInstance().getAllBudgetItems();
                    for (BudgetItem bi : budgetItems) {
                        if (bi.getCategory().equals(cat) && bi.getBudget().getBudgetID()==budget.getBudgetID()) {
                            bi.setCurrentState(bi.getCurrentState() - amount);
                            Communication.getInstance().editBudgetItem(bi);
                        }
                    }
                    JOptionPane.showMessageDialog(frmExpense, "Expense saved!");
                    frmExpense.dispose();
                    }
                    else{return;}
                } catch (Exception ex) {
                    Logger.getLogger(MonthlyBudgetController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("greska expense");
                }

            }
        });

    }

    public void resetForm() {
        frmExpense.getLblErrorName().setText("");
        frmExpense.getLblErrorAmount().setText("");
        frmExpense.getErrorLblDate().setText("");
    }

    public boolean validate() {
       
        if(frmExpense.getjDate().getDate()==null){
            frmExpense.getErrorLblDate().setText("Select a date");
            return false;
        }
        if (frmExpense.getInputName().getText().isEmpty()) {
            frmExpense.getLblErrorName().setText("Enter a name");
            return false;
        }
        if (frmExpense.getInputAmount().getText().isEmpty()) {
            frmExpense.getLblErrorAmount().setText("Enter amount");
            return false;
        }
        return true;
    }

    public MonthlyBudget getBudget() throws Exception {
        Date date = frmExpense.getjDate().getDate();
        User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        ArrayList<MonthlyBudget> mb = Communication.getInstance().getAllMonthlyBudgets();
        MonthlyBudget budget = new MonthlyBudget();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        for (MonthlyBudget m : mb) {
            if (m.getMonth() == month && m.getYear() == year && m.getUser().getUserID() == user.getUserID()) {
                budget = m;
            }
        }
        return budget;
    }

}
