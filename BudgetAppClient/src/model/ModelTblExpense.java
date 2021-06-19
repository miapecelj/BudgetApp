/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domain.Expense;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mia Pecelj
 */
public class ModelTblExpense extends AbstractTableModel{
    private ArrayList<Expense> expenses;
    private String column[]={"Name","Amount","Date","Category"};

    public ModelTblExpense(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }
    

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return expenses.size();
    }

    @Override
    public int getColumnCount() {
        return column.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Expense ex = expenses.get(rowIndex);
        switch(columnIndex){
            case 0:
                return ex.getName();
            case 1:
                return ex.getAmount();
            case 2:
                return ex.getDate();
            case 3:
                return ex.getCategory();
            default:
                return "greska";
        }
    }
    public Expense getExpenseAt(int row){
        return expenses.get(row);
    }

    @Override
    public String getColumnName(int col) {
        return column[col];
    }
    public void add(Expense e){
        expenses.add(e);
        fireTableDataChanged();
    }
    public void remove(int row){
        expenses.remove(row);
        fireTableDataChanged();
    }

    public void refresh() {
       fireTableDataChanged();
    }
    
    
}
