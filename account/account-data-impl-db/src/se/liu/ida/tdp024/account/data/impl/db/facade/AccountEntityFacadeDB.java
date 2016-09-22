package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import java.util.ServiceConfigurationError;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public long create(String personKey, String accountType, String bankKey) {
        
        EntityManager em = EMF.getEntityManager();
        
        try {
            
            Account account = new AccountDB();
            account.setPersonKey(personKey);
            account.setBankKey(bankKey);
            account.setAccountType(accountType);
            account.setHoldings(0);
            
            em.persist(account);
            em.getTransaction().commit();
            
            return account.getId();
            
            
        } catch (Exception e) {
            
            //Log stuff
            throw new ServiceConfigurationError("Commit failed");
            
        } finally {
            
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public Account find(long id) {
        
        EntityManager em = EMF.getEntityManager();
        
        try {
            return em.find(AccountDB.class, id);
        } catch(Exception e) {
            //logg stuff
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Account> findAll(String personKey) {
        EntityManager em = EMF.getEntityManager();
        
        try {
            Query query = em.createQuery("SELECT t from AccountDB t WHERE t.personKey = " + personKey);
            return query.getResultList();
        } catch(Exception e) {
            //log
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public String getType(long id) {
             
        try {
            Account account = find(id);
            return account.getAccountType();
        } catch(Exception e) {
            //logg stuff
            return null;
        }
    }

    @Override
    public void withdraw(long id, long amount) {
        
        try {
            Account account = find(id);
            account.setHoldings(account.getHoldings() - amount);
            
        } catch(Exception e) {
            //logg stuff
        }
  
    }

    @Override
    public void deposit(long id, long amount) {
        try {
            Account account = find(id);
            account.setHoldings(account.getHoldings() + amount);
            
        } catch(Exception e) {
            //logg stuff
        }
    }

    @Override
    public long checkBalance(long id) {
        try {
            Account account = find(id);
            return account.getHoldings();
            
        } catch(Exception e) {
            //logg stuff
            return -1; //fix later
        }
    }
    
}
