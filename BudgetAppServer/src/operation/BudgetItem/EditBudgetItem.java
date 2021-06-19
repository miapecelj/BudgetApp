/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.BudgetItem;

import domain.BudgetItem;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mia Pecelj
 */
public class EditBudgetItem extends AbstractGenericOperation{
     @Override
    protected void preconditions(Object param) throws Exception {
         System.out.println(param.getClass());
        if (param == null || !(param instanceof BudgetItem)) {
            throw new Exception("Invalid budget item data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        System.out.println("EVOOOOOOOO");
        repository.edit((BudgetItem) param);
    }
}
