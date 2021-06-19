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
import java.util.logging.Logger;

/**
 *
 * @author Mia Pecelj
 */
public class Expense implements Serializable,GenericEntity{
    private int expenseID;
    private Date date;
    private double amount;
    private String name;
    private Category category;
    private MonthlyBudget budget;

    public Expense(int expenseID, Date date, double amount, String name, Category category, MonthlyBudget budget) {
        this.expenseID = expenseID;
        this.date = date;
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.budget = budget;
    }

    

    public Expense() {
    }

    

    public MonthlyBudget getBudget() {
        return budget;
    }

    public void setBudget(MonthlyBudget budget) {
        this.budget = budget;
    }

   

    public int getExpenseID() {
        return expenseID;
    }

    public void setId(int expenseID) {
        this.expenseID = expenseID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Expense other = (Expense) obj;
        if (this.expenseID != other.expenseID) {
            return false;
        }
        if (!Objects.equals(this.budget, other.budget)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Expense{" + "expenseID=" + expenseID + ", date=" + date + ", amount=" + amount + ", name=" + name + ", category=" + category + '}';
    }

    @Override
    public String getTableName() {
        return "expense";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "date,name,amount,categoryID,budgetID";
    }

    @Override
    public String getInsertValues() {
        return "'"+new java.sql.Date(date.getTime())+"','"+name+"',"+amount+","+category.getID()+","+budget.getBudgetID();
    }

    

    @Override
    public String setAtrValue() {
        return "";
    }

    @Override
    public String getWhereCondition() {
        return "expense.expenseID="+expenseID;
    }

    @Override
    public String getJoin() {
        return " INNER JOIN CATEGORY c ON (expense.categoryID=c.categoryID) INNER JOIN MONTHLYBUDGET m on (m.budgetID=expense.budgetID) INNER JOIN USER u on (m.userID=u.userID)";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        Category category = new Category(rs.getString("c.name"),rs.getInt("c.categoryID"));
        User user = new User(rs.getInt("u.userID"),rs.getString("u.firstname"),rs.getString("u.lastname"),rs.getString("u.password"),rs.getString("u.username"));
        MonthlyBudget mb = new MonthlyBudget(user,rs.getInt("m.budgetID"),rs.getInt("m.month"),rs.getInt("m.year"),rs.getDouble("m.monthlyLimit"),rs.getDouble("m.currentState"));
        return new Expense(rs.getInt("expense.expenseId"),rs.getDate("expense.date"),rs.getDouble("expense.amount"),rs.getString("expense.name"),category,mb);
    }
 
    

    
  
    
}
