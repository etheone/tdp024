/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import java.util.ServiceConfigurationError;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

/**
 *
 * @author emini901
 */
public class TransactionEntityFacadeDB implements TransactionEntityFacade{

    @Override
    public long create(String type, long amount, 
            String created, String status, Account account) {
        
        EntityManager em = EMF.getEntityManager();
        
        try {
            
            em.getTransaction().begin();
            
            Transaction transaction = new TransactionDB();
            transaction.setType(type);
            transaction.setAmount(amount);
            transaction.setDate(created);
            transaction.setStatus(status);
            transaction.setAccount(account);
            
            em.persist(transaction);
            em.getTransaction().commit();
            
            return transaction.getId();
        } catch (Exception e) {
            // Log stuff
            throw new ServiceConfigurationError("Commit failed.");
        } finally {
            
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public Transaction find(long id) {
        EntityManager em = EMF.getEntityManager();
        
        try {
            return em.find(TransactionDB.class, id);
        } catch(Exception e) {
            //logg stuff
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Transaction> findAll(long id) {
        EntityManager em = EMF.getEntityManager();
        
        try {
            Query query = em.createQuery("SELECT t from TransactionDB t WHERE t.account.id = :id");
            query.setParameter("id", id);
            List<Transaction> queryList = query.getResultList();
            System.out.println("****************************QUERYLIST*****************'");
            System.out.println(queryList);
            return queryList;
        } catch(Exception e) {
            //log
            return null;
        } finally {
            em.close();
        }
    }
    
}
