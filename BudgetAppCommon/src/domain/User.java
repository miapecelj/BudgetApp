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
 * @author Mia Pecelj
 */
public class User implements Serializable,GenericEntity{
    private int userID;
    private String firstname;
    private String lastname;
    private String password;
    private String username;

    public User() {
    }

    public User(int userID, String firstName, String lastName, String password, String userName) {
        this.userID = userID;
        this.firstname = firstName;
        this.lastname = lastName;
        this.password = password;
        this.username = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setId(int userID) {
        this.userID = userID;
    }

    


    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public String toString() {
        return "User{" + ", firstName=" + firstname + ", lastName=" + lastname + ", password=" + password + ", userName=" + username + '}';
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
        final User other = (User) obj;
        if (this.userID != other.userID) {
            return false;
        }
        return true;
    }

   @Override
    public String getTableName() {
        return "user";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "username, password, firstname, lastname";
    }

    @Override
    public String getInsertValues() {
        return "'"+username+"','"+password+"','"+firstname+"','"+lastname+"'";
    }

    @Override
    public String setAtrValue() {
        return "username=" + (username == null ? null : "'" + username + "'")
                 +"', " + "password=" + (password == null ? null : "'" + password + "'")
                 +"', " + "firstname=" + (firstname == null ? null : "'" + firstname + "'")
                 +"', " + "lastname=" + (lastname == null ? null : "'" + lastname + "'");
    }

    @Override
    public String getWhereCondition() {
        return "userid="+userID;
    }

    @Override
    public String getJoin() {
        return "";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new User(rs.getInt("user.userId"),rs.getString("user.firstname"),rs.getString("user.lastname"),rs.getString("user.username"),rs.getString("user.password"));
    }

    
    
}
