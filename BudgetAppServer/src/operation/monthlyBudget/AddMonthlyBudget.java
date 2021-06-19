/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.monthlyBudget;

import domain.BudgetItem;
import domain.MonthlyBudget;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mia Pecelj
 */
public class AddMonthlyBudget extends AbstractGenericOperation{
     @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof MonthlyBudget)) {
            throw new Exception("Invalid monthly budget data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.add((MonthlyBudget) param);
        for(BudgetItem bi: ((MonthlyBudget)param).getBudgetItems()){
            bi.setBudget((MonthlyBudget) param);
            repository.add(bi);
        }
    }
    
}
