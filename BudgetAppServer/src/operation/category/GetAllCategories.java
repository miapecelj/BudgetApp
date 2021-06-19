/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.category;

import domain.Category;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mia Pecelj
 */
public class GetAllCategories extends AbstractGenericOperation{
    List<Category> tasks;

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Category)) {
            throw new Exception("Invalid category data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
       tasks = repository.getAll((Category) param);
    }

    public List<Category> getTasks() {
        return tasks;
    }
}
