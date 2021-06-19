/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import domain.BudgetItem;
import domain.Category;
import domain.Expense;
import domain.MonthlyBudget;
import domain.User;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cartman
 */
public class Communication {

    Socket socket;
    Sender sender;
    Receiver receiver;
    private static Communication instance;

    private Communication() throws Exception {
        socket = new Socket("127.0.0.1", 9000);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    public static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public User login(String username, String password) throws Exception {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        Request request = new Request(Operation.LOGIN, user);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (User) response.getResult();
        } else {
            throw response.getException();
        }
    }


    public void saveCategory(Category category) throws Exception {
        Request request = new Request(Operation.ADD_CATEGORY, category);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            Category newCategory = (Category) response.getResult();
            category.setId(newCategory.getID());
            System.out.println(category.getID());
        } else {
            throw response.getException();
        }
    }

    public ArrayList<Category> getAllCategories() throws Exception {
        Request request = new Request(Operation.GET_ALL_CATEGORIES, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (ArrayList<Category>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void addMonhlyBudget(MonthlyBudget mb) throws Exception {
        Request request = new Request(Operation.ADD_MONTHLY_BUDGET, mb);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            MonthlyBudget newMB = (MonthlyBudget) response.getResult();
            mb.setId(newMB.getBudgetID());
        } else {
            throw response.getException();
        }
    }

    

    public ArrayList<BudgetItem> getAllBudgetItems() throws Exception {
        Request request = new Request(Operation.GET_ALL_BUDGETITEMS,null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (ArrayList<BudgetItem>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    

    public void addExpense(Expense e) throws Exception {
        Request request = new Request(Operation.ADD_EXPENSE, e);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            Expense newExpense = (Expense) response.getResult();
            e.setId(newExpense.getExpenseID());
        } else {
            throw response.getException();
        }
    }

    public void editMonthlyBudget(MonthlyBudget mb) throws Exception {
        Request request = new Request(Operation.EDIT_MONTHLY_BUDGET,mb);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public void editBudgetItem(BudgetItem bi) throws Exception {
        System.out.println(bi.getClass());
        Request request = new Request(Operation.EDIT_BUDGET_ITEM,bi);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public void RemoveBudgetItem(BudgetItem bi) throws Exception {
         Request request=new Request(Operation.DELETE_BUDGET_ITEM, bi);
        sender.send(request);
        Response response=(Response)receiver.receive();
        if(response.getException()==null){
            
        }else{
            throw response.getException();
        }
    }

    public void addBudgetItem(BudgetItem budgetItem) throws Exception {
        Request request = new Request(Operation.ADD_BUDGET_ITEM, budgetItem);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            BudgetItem newBI = (BudgetItem) response.getResult();
            budgetItem.setId(newBI.getBudgetItemID());
        } else {
            throw response.getException();
        }
    }

    public ArrayList<Expense> getAllExpenses() throws Exception {
        Request request = new Request(Operation.GET_ALL_EXPENSES,null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (ArrayList<Expense>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void deleteExpense(Expense expense) throws Exception {
        Request request=new Request(Operation.DELETE_EXPENSE, expense);
        sender.send(request);
        Response response=(Response)receiver.receive();
        if(response.getException()==null){
            
        }else{
            throw response.getException();
        }
    }



    public ArrayList<MonthlyBudget> getAllMonthlyBudgets() throws Exception {
        Request request = new Request(Operation.GET_ALL_MONTHLY_BUDGETS,null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (ArrayList<MonthlyBudget>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void editMonthlyBudgetItems(MonthlyBudget budget) throws Exception {
        Request request = new Request(Operation.EDIT_ALL_BUDGET_ITEMS,budget);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    

}
