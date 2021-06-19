/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.BudgetItem;

import domain.BudgetItem;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mia Pecelj
 */
public class GetAllBudgetItems extends AbstractGenericOperation{
    List<BudgetItem> projects;
    
    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof BudgetItem)) {
            throw new Exception("Invalid monthly budget data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        projects = repository.getAll((BudgetItem) param);
    }

    public List<BudgetItem> getBudgetItems() {
        return projects;
    }
}
