/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author Mia Pecelj
 */
public class Category implements Serializable,GenericEntity{
    private int categoryID;
    private String name;

    public Category() {
    }

    public Category(String Name, int ID) {
        this.name = Name;
        this.categoryID = ID;
    }

    public int getID() {
        return categoryID;
    }

    public void setId(int ID) {
        this.categoryID = ID;
    }

 

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    @Override
    public String toString() {
        return name;
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
        final Category other = (Category) obj;
        if (this.categoryID != other.categoryID) {
            return false;
        }
        return true;
    }

    @Override
    public String getTableName() {
        return "category";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "name";
    }

    @Override
    public String getInsertValues() {
        return "'"+name+"'";
    }

    @Override
    public String setAtrValue() {
        return "name='"+name+"'";
    }

    @Override
    public String getWhereCondition() {
        return "categoryID="+categoryID;
    }

    @Override
    public String getJoin() {
        return "";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new Category(rs.getString("name"),rs.getInt("categoryID"));
    }

    

   
    
    
}
