/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.monthlyBudget;

import domain.BudgetItem;
import domain.MonthlyBudget;
import domain.Signal;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mia Pecelj
 */
public class EditMonthlyBudgetItems extends AbstractGenericOperation {
     @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof MonthlyBudget)) {
            throw new Exception("Invalid monthly budget data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        for(BudgetItem bi : ((MonthlyBudget)param).getBudgetItems()){
            if(bi.getSigna().equals(Signal.Add)){
                repository.add(bi);
            }
            if(bi.getSigna().equals(Signal.Edit)){
                repository.edit(bi);
            }
            if(bi.getSigna().equals(Signal.Delete)){
                repository.delete(bi);
            }
        }
       
         
               
    }
} 
