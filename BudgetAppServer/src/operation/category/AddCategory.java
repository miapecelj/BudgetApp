/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.category;

import domain.Category;
import java.util.ArrayList;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mia Pecelj
 */
public class AddCategory extends AbstractGenericOperation{
    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Category)) {
            throw new Exception("Invalid category data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ArrayList<Category> cat = (ArrayList<Category>) repository.getAll(new Category());
        for(Category c: cat){
            if(((Category)param).getName().equals(c.getName())){
                throw new Exception("Category exists!");
            }
        }
        repository.add((Category) param);
    }
}
