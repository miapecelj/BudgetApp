/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinator;

import Controller.BudgetItemController;
import Controller.CategoryController;
import Controller.ExpenseController;
import Controller.LoginController;
import Controller.MainContoller;
import Controller.MonthlyBudgetController;
import Controller.ViewExpensesController;
import Controller.ViewMonthlyBudgetController;
import domain.BudgetItem;
import frm.FrmBudgetItem;
import frm.FrmCategory;
import frm.FrmExpense;
import frm.FrmLogin;
import frm.FrmMain;
import frm.FrmMonthlyBudget;
import frm.FrmViewExpenses;
import frm.FrmViewMonthlyBudget;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author laptop-02
 */
public class MainCoordinator {

    private static MainCoordinator instance;

    private final MainContoller mainContoller;
    private final MonthlyBudgetController monthlyBudgetController;
    private final ViewMonthlyBudgetController viewMonthlyBudgetController;
    
    //this can be in some other Singleton class
    private final Map<String, Object> params;

    private MainCoordinator() {
        mainContoller = new MainContoller(new FrmMain());
        monthlyBudgetController = new MonthlyBudgetController(new FrmMonthlyBudget(mainContoller.getFrmMain(),true));
        viewMonthlyBudgetController = new ViewMonthlyBudgetController(new FrmViewMonthlyBudget(mainContoller.getFrmMain(), true));
        params = new HashMap<>();
    }

    public MonthlyBudgetController getMonthlyBudgetController() {
        return monthlyBudgetController;
    }

    public ViewMonthlyBudgetController getViewMonthlyBudgetController() {
        return viewMonthlyBudgetController;
    }

    public static MainCoordinator getInstance() {
        if (instance == null) {
            instance = new MainCoordinator();
        }
        return instance;
    }
    public void openLoginForm() {
        LoginController loginContoller = new LoginController(new FrmLogin());
        loginContoller.openForm();
    }

    public void openMainForm() {
        mainContoller.openForm();
    }
    public void openCategoryForm(){
        CategoryController categoryController = new CategoryController(new FrmCategory(mainContoller.getFrmMain(),true));
        categoryController.openForm();
        
    }



    public MainContoller getMainContoller() {
        return mainContoller;
    }


    public void addParam(String name, Object key) {
        params.put(name, key);
    }

    public Object getParam(String name) {
        return params.get(name);
    }



    public void openBudgetItemForm(String param) throws Exception {

        BudgetItemController budgetItemController = new BudgetItemController(new FrmBudgetItem(mainContoller.getFrmMain(),true),param);
        budgetItemController.openForm();

    }
    public void openMonthlyBudgetForm(){

        monthlyBudgetController.openForm();
    }

    public void addBudgetItem(BudgetItem budgetItem,String param) throws Exception {
        if(param.equals("create")){
         monthlyBudgetController.addBudgetItem(budgetItem);
        }
        else{
        viewMonthlyBudgetController.addBudgetItem(budgetItem);
        }
        
    }

    public void openViewMonthlyBudgetForm() throws Exception {
       viewMonthlyBudgetController.openForm();
    }

    public void openExpenseForm() throws Exception {
        ExpenseController ec = new ExpenseController(new FrmExpense(mainContoller.getFrmMain(), true));
        ec.openForm();
    }

    public void openExpenseViewForm() throws Exception {
        ViewExpensesController vec = new ViewExpensesController(new FrmViewExpenses(mainContoller.getFrmMain(),true));
        vec.openForm();
    }

    public void editBudgetItem(BudgetItem budgetItem) throws Exception {
        viewMonthlyBudgetController.editBudgetItem(budgetItem);
    }

    


    

    
    

  
   
}
