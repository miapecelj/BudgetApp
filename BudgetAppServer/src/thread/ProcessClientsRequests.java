/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import controller.Controller;
import domain.BudgetItem;
import domain.Category;
import domain.Expense;
import domain.MonthlyBudget;
import domain.User;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Korisnik
 */
public class ProcessClientsRequests extends Thread {

    Socket socket;
    Sender sender;
    Receiver receiver;
    ServerSocket serverSocket;
    boolean run;

    public ProcessClientsRequests(Socket socket, ServerSocket serverSocket) {
      this.socket = socket;
        sender=new Sender(socket);
        receiver=new Receiver(socket);
        this.serverSocket = serverSocket;
        this.run = true;

    }

   

    

    @Override
    public void run() {

        while (true) {
            try {
                Request request = (Request) receiver.receive();
                Response response = new Response();
                try {
                    switch (request.getOperation()) {
                        case LOGIN:
                            User user = (User) request.getArgument();
                            response.setResult(Controller.getInstance().login(user.getUserName(), user.getPassword()));
                            break;
                        case GET_ALL_CATEGORIES:
                            response.setResult(Controller.getInstance().getAllCategories());
                            break;
                        case ADD_CATEGORY:
                            Category categoryInsert = (Category) request.getArgument();
                            Controller.getInstance().addCategory(categoryInsert);
                            response.setResult(categoryInsert);
                            break;
                        case EDIT_BUDGET_ITEM:
                            BudgetItem biEdit = (BudgetItem) request.getArgument();
                            System.out.println(biEdit.getClass());

                            Controller.getInstance().editBudgetItem(biEdit);
                            break;
                        case EDIT_MONTHLY_BUDGET:
                            MonthlyBudget mbEdit = (MonthlyBudget) request.getArgument();
                            Controller.getInstance().editMonthlyBudget(mbEdit);
                            break;
                        case DELETE_BUDGET_ITEM:
                            BudgetItem biDelete = (BudgetItem) request.getArgument();
                            Controller.getInstance().deleteBudgetItem(biDelete);
                            break;
                        case DELETE_EXPENSE:
                            Expense exDelete = (Expense) request.getArgument();
                            Controller.getInstance().deleteExpense(exDelete);
                            break;
                        case ADD_MONTHLY_BUDGET:
                            MonthlyBudget mbInsert = (MonthlyBudget) request.getArgument();
                            Controller.getInstance().addMonthlyBudget(mbInsert);
                            response.setResult(mbInsert);
                            break;
                        case ADD_EXPENSE:
                            Expense expenseInsert = (Expense) request.getArgument();
                            Controller.getInstance().addExpense(expenseInsert);
                            response.setResult(expenseInsert);
                            break;
                        case ADD_BUDGET_ITEM:
                            BudgetItem biInsert = (BudgetItem) request.getArgument();
                            Controller.getInstance().addBudgetItem(biInsert);
                            response.setResult(biInsert);
                            break;
                        case GET_ALL_BUDGETITEMS:
                            response.setResult(Controller.getInstance().getAllBudgetItems());
                            break;
                        case GET_ALL_EXPENSES:
                            response.setResult(Controller.getInstance().getAllExpenses());
                            break;
                        case GET_ALL_MONTHLY_BUDGETS:
                            response.setResult(Controller.getInstance().getAllMonthlyBudgets());
                            break;
                        case EDIT_ALL_BUDGET_ITEMS:
                            MonthlyBudget mb = (MonthlyBudget) request.getArgument();
                            System.out.println(mb.getClass());

                            Controller.getInstance().editMonthlyBudgetItems(mb);
                            break;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setException(e);
                }
                sender.send(response);
            } catch (Exception ex) {
                Logger.getLogger(ProcessClientsRequests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
