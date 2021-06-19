/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.expense;

import domain.Expense;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mia Pecelj
 */
public class DeleteExpense extends AbstractGenericOperation {
    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Expense)) {
            throw new Exception("Invalid expense data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.delete((Expense) param);
    }
}
