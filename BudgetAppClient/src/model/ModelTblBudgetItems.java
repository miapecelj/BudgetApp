/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import domain.BudgetItem;
import domain.Category;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mia Pecelj
 */
public class ModelTblBudgetItems extends AbstractTableModel {

    ArrayList<BudgetItem> budgetItems;
    String column[]={"Name","Limit","CurrentState"};

    public ArrayList<BudgetItem> getBudgetItems() {
        return budgetItems;
    }

    public void setBudgetItems(ArrayList<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
    }

    public ModelTblBudgetItems() {
        budgetItems = new ArrayList<>();
    }

    public ModelTblBudgetItems(ArrayList<BudgetItem> bi) {
        this.budgetItems = bi;
    }

    public void add(BudgetItem c) {
        budgetItems.add(c);
        fireTableDataChanged();
    }

    public void remove(int row) {
        budgetItems.remove(row);
        fireTableDataChanged();
    }
    public void remove(BudgetItem row) {
        budgetItems.remove(row);
        fireTableDataChanged();
    }

    public ArrayList<BudgetItem> getList() {
        return budgetItems;
    }

    @Override
    public int getRowCount() {
        return budgetItems.size();
    }

    @Override
    public int getColumnCount() {
        return column.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BudgetItem bi = budgetItems.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return bi.getCategory().getName();
            case 1:
                return bi.getLimit();
            case 2:
                return bi.getCurrentState();
        }
        return null;

    }
    public BudgetItem getBudgetItemAt(int row){
        return budgetItems.get(row);
    }
    @Override
    public String getColumnName(int col) {
        return column[col];
    }
    
    public BudgetItem getCategoryAt(int row){
        return budgetItems.get(row);
    }

    public void refresh() {
        fireTableDataChanged();
    }
    public void clear(){
        budgetItems.clear();
        fireTableDataChanged();
    }

    public void edit(BudgetItem bi, double limit,double currentState) {
        int index =budgetItems.indexOf(bi);
        BudgetItem b = budgetItems.get(index);
        b.setLimit(limit);
        b.setCurrentState(currentState);
        fireTableDataChanged();
    }

   
    

}
