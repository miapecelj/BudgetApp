/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Milos Milic
 */
public interface GenericEntity extends Serializable {

   String getTableName();

    String getColumnNamesForInsert();

    String getInsertValues();

    void setId(int id);
    
    String setAtrValue();
    
    String getWhereCondition();
    
    String getJoin();
    
    GenericEntity getNewRecord(ResultSet rs) throws SQLException;

}
