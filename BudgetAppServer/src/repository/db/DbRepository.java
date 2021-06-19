/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import repository.Repository;



/**
 *
 * @author laptop-02
 * @param <T>
 */
public interface DbRepository<T>  extends Repository<T>{
    default public void connect() throws Exception{
        ConnectionFactory.getInstance().getConnection();
    }
    
    default public void disconnect() throws Exception{
        ConnectionFactory.getInstance().getConnection().close();
    }
    
    default public void commit() throws Exception{
        ConnectionFactory.getInstance().getConnection().commit();
    }
    
    default public void rollback() throws Exception{
        ConnectionFactory.getInstance().getConnection().rollback();
    }
}
