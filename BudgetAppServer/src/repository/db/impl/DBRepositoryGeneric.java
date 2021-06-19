/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.BudgetItem;
import domain.Category;
import domain.GenericEntity;
import domain.MonthlyBudget;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import repository.db.ConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Mia Pecelj
 */
public class DBRepositoryGeneric implements DbRepository<GenericEntity> {

    @Override
    public List<GenericEntity> getAll(GenericEntity entity) throws Exception {
        try {
            Connection connection = ConnectionFactory.getInstance().getConnection();
            List<GenericEntity> entities = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ")
                    .append(entity.getTableName())
                    .append(entity.getJoin());

            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                GenericEntity e = entity.getNewRecord(rs);
                entities.add(e);
            }
            rs.close();
            statement.close();
            return entities;
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void add(GenericEntity entity) throws Exception {
        try {
            Connection connection = ConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ")
                    .append(entity.getTableName())
                    .append(" (").append(entity.getColumnNamesForInsert()).append(")")
                    .append(" VALUES (")
                    .append(entity.getInsertValues())
                    .append(")");
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rsKey = statement.getGeneratedKeys();
            if (rsKey.next()) {
                int id = rsKey.getInt(1);
                entity.setId(id);
            }
            statement.close();
            rsKey.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void edit(GenericEntity entity) throws Exception {
        try {
            Connection connection = ConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ")
                    .append(entity.getTableName())
                    .append(" SET ").append(entity.setAtrValue())
                    .append(" WHERE ")
                    .append(entity.getWhereCondition());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void delete(GenericEntity entity) throws Exception {
        try {
            Connection connection = ConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ")
                    .append(entity.getTableName())
                    .append(" WHERE ")
                    .append(entity.getWhereCondition());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public List<GenericEntity> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public GenericEntity getByID(GenericEntity param) throws Exception {
        try {
            System.out.println(param.getWhereCondition());
            Connection connection = ConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ")
                    .append(param.getTableName())
                    .append(param.getJoin())
                    .append(" WHERE ")
                    .append(param.getWhereCondition());   
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            GenericEntity e = null;
            if (rs.next()) {
                e = param.getNewRecord(rs);
//                if (param instanceof MonthlyBudget) {
//                    MonthlyBudget mb = (MonthlyBudget) e;
//                    mb.setBudgetItems(getAllBudgetItems(mb));
//                }
            }
            rs.close();
            statement.close();
            return e;
         } catch (SQLException ex) {
            throw ex;
        }
    }

//    private List<BudgetItem> getAllBudgetItems(MonthlyBudget mb) {
//        List<BudgetItem> budgetItems = new ArrayList<>();
//         try {
//            String sql = "SELECT * FROM BUDGETITEM INNER JOIN CATEGORY c on(c.categoryID=budgetItem.categoryID) WHERE budgetid="+mb.getBudgetID();
//            System.out.println(sql);
//            Connection connection = ConnectionFactory.getInstance().getConnection();
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery();
//            while(rs.next()) {
//                Category category = new Category();
//                category.setId(rs.getInt("categoryID"));
//                category.setName(rs.getString("name"));
//                BudgetItem bi = new BudgetItem();
//                bi.setId(rs.getInt("budgetid"));
//                bi.setCurrentState(rs.getDouble("currentState"));
//                bi.setLimit(rs.getDouble("limitBI"));
//                bi.setCategory(category);
//                bi.setBudget(mb);
//                budgetItems.add(bi);
//            }
//            rs.close();
//            statement.close();
//            return budgetItems;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//    

}
