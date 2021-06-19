/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Mia Pecelj
 */
public class BudgetItem implements Serializable,GenericEntity{
    private int budgetItemID;
    private double limit;
    private Category category;
    private MonthlyBudget budget;
    private double currentState;
    private Signal signa;
    public BudgetItem() {
    }

    public BudgetItem(int budgetItemID, double limit, Category category, MonthlyBudget budget, double currentState) {
        this.budgetItemID = budgetItemID;
        this.limit = limit;
        this.category = category;
        this.budget = budget;
        this.currentState = currentState;
    }

    public Signal getSigna() {
        return signa;
    }

    public void setSigna(Signal signa) {
        this.signa = signa;
    }
    

    public double getCurrentState() {
        return currentState;
    }

    public void setCurrentState(double currentState) {
        this.currentState = currentState;
    }

    

    public int getBudgetItemID() {
        return budgetItemID;
    }

    public void setId(int budgetItemID) {
        this.budgetItemID = budgetItemID;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MonthlyBudget getBudget() {
        return budget;
    }

    public void setBudget(MonthlyBudget budget) {
        this.budget = budget;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BudgetItem other = (BudgetItem) obj;
        if (this.budgetItemID != other.budgetItemID) {
            return false;
        }
        if (!Objects.equals(this.budget, other.budget)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BudgetItem{" + "budgetItemID=" + budgetItemID + ", limit=" + limit + ", category=" + category + ", budget=" + budget + '}';
    }

    @Override
    public String getTableName() {
        return "budgetItem";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "LimitBI,categoryID,budgetID,currentState";
    }

    @Override
    public String getInsertValues() {
        return ""+limit+","+category.getID()+","+budget.getBudgetID()+","+currentState;
    }

    

    @Override
    public String setAtrValue() {
        return "limitBI="+limit+", currentState="+currentState;
    }

    @Override
    public String getWhereCondition() {
        System.out.println("budgetItem.budgetItemID="+budgetItemID+" AND budgetItem.budgetID=" +budget.getBudgetID());
        return "budgetItem.budgetItemID="+budgetItemID+" AND budgetItem.budgetID=" +budget.getBudgetID();
    }

    @Override
    public String getJoin() {
        return " INNER JOIN monthlyBudget b on (budgetItem.budgetID=b.budgetID) INNER JOIN CATEGORY c on (budgetItem.categoryID=c.categoryID) INNER JOIN USER u on(u.userID=b.userID)";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        Category category = new Category(rs.getString("c.name"),rs.getInt("c.categoryID"));
        User user = new User(rs.getInt("u.userID"),rs.getString("u.firstname"),rs.getString("u.lastname"),rs.getString("u.password"),rs.getString("u.username"));
        MonthlyBudget mb = new MonthlyBudget(user,rs.getInt("b.budgetID"),rs.getInt("b.month"),rs.getInt("b.year"),rs.getDouble("b.monthlyLimit"),rs.getDouble("b.currentState"));
        return new BudgetItem(rs.getInt("budgetItem.budgetItemID"),rs.getDouble("budgetItem.limitBI"),category,mb,rs.getDouble("budgetItem.currentState"));
    }

    
    
   

 

    

   

   
    
}
