/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.expense;

import domain.Expense;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mia Pecelj
 */
public class GetAllExpenses extends AbstractGenericOperation{
    
    List<Expense> expenses;

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Expense)) {
            throw new Exception("Invalid project task data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
       expenses = repository.getAll((Expense) param);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
    
}
