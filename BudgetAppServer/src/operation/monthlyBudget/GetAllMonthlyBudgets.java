/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.monthlyBudget;

import domain.BudgetItem;
import domain.MonthlyBudget;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mia Pecelj
 */
public class GetAllMonthlyBudgets extends AbstractGenericOperation {

    List<MonthlyBudget> budgets;

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof MonthlyBudget)) {
            throw new Exception("Invalid monthly budget data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        budgets = repository.getAll((MonthlyBudget) param);
        for (MonthlyBudget mb : budgets) {
            BudgetItem bi =new BudgetItem();
            bi.setBudget(mb);
            mb.setBudgetItems(repository.getAll(bi));
        }
    }

    public List<MonthlyBudget> getMonthlyBudgets() {
        return budgets;
    }
}
