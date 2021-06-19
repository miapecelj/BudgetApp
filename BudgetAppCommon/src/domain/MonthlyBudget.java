/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Mia Pecelj
 */
public class MonthlyBudget implements Serializable,GenericEntity{
    private User user;
    private int budgetID;
    private int month;
    private int year;
    private Double limit;
    private Double currentState;
    private List<BudgetItem> budgetItems=new ArrayList<>();

    public MonthlyBudget() {
    }

    public MonthlyBudget(User user, int budgetID, int Month, int Year, Double Limit, Double CurrentState) {
        this.user = user;
        this.budgetID = budgetID;
        this.month = Month;
        this.year = Year;
        this.limit = Limit;
        this.currentState = CurrentState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    

    public int getBudgetID() {
        return budgetID;
    }

    public void setId(int budgetID) {
        this.budgetID = budgetID;
    }

   

    public List<BudgetItem> getBudgetItems() {
        return budgetItems;
    }

    public void setBudgetItems(List<BudgetItem> BudgetItems) {
        this.budgetItems = BudgetItems;
    }

    

    public int getMonth() {
        return month;
    }

    public void setMonth(int Month) {
        this.month = Month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int Year) {
        this.year = Year;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double Limit) {
        this.limit = Limit;
    }

    public Double getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Double CurrentState) {
        this.currentState = CurrentState;
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
        final MonthlyBudget other = (MonthlyBudget) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (this.budgetID != other.budgetID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MonthlyBudget{" + "user=" + user + ", budgetID=" + budgetID + ", Month=" + month + ", Year=" + year + ", Limit=" + limit + ", CurrentState=" + currentState + ", BudgetItems=" + budgetItems + '}';
    }

    @Override
    public String getTableName() {
        return "monthlybudget";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "month,year,monthlyLimit,currentState,userID";
    }

    @Override
    public String getInsertValues() {
        return month+","+year+","+limit+","+currentState+","+user.getUserID();
    }

    

    @Override
    public String setAtrValue() {
        return " monthlylimit="+limit+", currentState="+currentState;
    }

    @Override
    public String getWhereCondition() {
        return "monthlybudget.budgetId="+budgetID+" AND monthlyBudget.userID="+user.getUserID();
    }

    @Override
    public String getJoin() {
        return " INNER JOIN USER u on (u.userID=monthlybudget.userID)";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt("u.userID"),rs.getString("u.firstname"),rs.getString("u.lastname"),rs.getString("u.password"),rs.getString("u.username"));
        return new MonthlyBudget(user,rs.getInt("monthlyBudget.budgetID"),rs.getInt("monthlyBudget.month"),rs.getInt("monthlyBudget.year"),rs.getDouble("monthlyBudget.monthlyLimit"),rs.getDouble("monthlyBudget.currentState"));
    }

    

    

    

   
    
    
    
}
