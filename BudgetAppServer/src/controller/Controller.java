/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.BudgetItem;
import domain.Category;
import domain.Expense;
import domain.MonthlyBudget;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;
import operation.BudgetItem.AddBudgetItem;
import operation.BudgetItem.DeleteBudgetItem;
import operation.BudgetItem.EditBudgetItem;
import operation.BudgetItem.GetAllBudgetItems;
import operation.category.AddCategory;
import operation.category.GetAllCategories;
import operation.expense.AddExpense;
import operation.expense.DeleteExpense;
import operation.expense.GetAllExpenses;
import operation.monthlyBudget.AddMonthlyBudget;
import operation.monthlyBudget.EditMonthlyBudget;
import operation.monthlyBudget.EditMonthlyBudgetItems;
import operation.monthlyBudget.GetAllMonthlyBudgets;
import operation.user.GetAllUsers;
import repository.Repository;
import repository.db.DbRepository;
import view.controller.MainCoordinator;

/**
 *
 * @author laptop-02
 */
public class Controller {

    private static Controller controller;

    private Controller() {

    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public User login(String username, String password) throws Exception {
        List<User> users = getAllUsers();
        System.out.println(users);
        for (User user : users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                String korisnici =  MainCoordinator.getInstance().getMainController().getFrmMain().getLblKorisnici().getText();
                MainCoordinator.getInstance().getMainController().getFrmMain().getLblKorisnici().setText(korisnici+" "+user.getUserName()+", ");

                return user;
            }
        }
        throw new Exception("Unknown user");
    }

    public List<User> getAllUsers() throws Exception {
        AbstractGenericOperation operation = new GetAllUsers();
        operation.execute(new User());
        return ((GetAllUsers) operation).getUsers();
    }

    public void addCategory(Category category) throws Exception {
        AbstractGenericOperation operation = new AddCategory();
        operation.execute(category);
    }

    public ArrayList<Category> getAllCategories() throws Exception {
        AbstractGenericOperation operation = new GetAllCategories();
        operation.execute(new Category());
        return (ArrayList<Category>) ((GetAllCategories) operation).getTasks();
    }

    public void addMonthlyBudget(MonthlyBudget mb) throws Exception {
        AbstractGenericOperation operation = new AddMonthlyBudget();
        operation.execute(mb);
    }

    public List<BudgetItem> getAllBudgetItems() throws Exception {
        AbstractGenericOperation operation = new GetAllBudgetItems();
        operation.execute(new BudgetItem());
        return ((GetAllBudgetItems) operation).getBudgetItems();
    }

    public void editBudgetItem(BudgetItem biEdit) throws Exception {
        System.out.println(biEdit.getClass());

        AbstractGenericOperation operation = new EditBudgetItem();
        operation.execute(biEdit);
    }

    public void editMonthlyBudget(MonthlyBudget mbEdit) throws Exception {
        AbstractGenericOperation operation = new EditMonthlyBudget();
        operation.execute(mbEdit);
    }

    public void addExpense(Expense expenseInsert) throws Exception {
        AbstractGenericOperation operation = new AddExpense();
        operation.execute(expenseInsert);
    }

    public void deleteBudgetItem(BudgetItem biDelete) throws Exception {
        AbstractGenericOperation operation = new DeleteBudgetItem();
        operation.execute(biDelete);
    }

    public void addBudgetItem(BudgetItem biInsert) throws Exception {
        AbstractGenericOperation operation = new AddBudgetItem();
        operation.execute(biInsert);
    }

    public Object getAllExpenses() throws Exception {
        AbstractGenericOperation operation = new GetAllExpenses();
        operation.execute(new Expense());
        return ((GetAllExpenses) operation).getExpenses();
    }

    public void deleteExpense(Expense exDelete) throws Exception {
        AbstractGenericOperation operation = new DeleteExpense();
        operation.execute(exDelete);
    }

    public Object getAllMonthlyBudgets() throws Exception {
        AbstractGenericOperation operation = new GetAllMonthlyBudgets();
        operation.execute(new MonthlyBudget());
        return ((GetAllMonthlyBudgets) operation).getMonthlyBudgets();
    }

    public void editMonthlyBudgetItems(MonthlyBudget mb) throws Exception {
        AbstractGenericOperation operation = new EditMonthlyBudgetItems();
        operation.execute(mb);
    }

}
