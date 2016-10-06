package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public long create(String personKey, String accountType, String bankKey) {
        
        EntityManager em = EMF.getEntityManager();
        try {
            
            em.getTransaction().begin();
            
            Account account = new AccountDB();
            account.setPersonKey(personKey);
            account.setBankKey(bankKey);
            account.setAccountType(accountType);
            account.setHoldings(0);
            //account.setTransactions(new ArrayList<Transaction>());
            
            em.persist(account);
            em.getTransaction().commit();
            
            return account.getId();
            
            
        } catch (Exception e) {
            
            throw new ServiceConfigurationError("commit failed");
            
        } finally {
            
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void addTransaction(long accountId, long transactionId)
    {
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Account account = em.find(AccountDB.class, accountId);
            
            Transaction transaction = em.find(TransactionDB.class, transactionId);
            transaction.setAccount(account);
            
            //account.addTransactionToAccount(transaction);
            
            if(transaction.getStatus().equals("OK")) {
                if (transaction.getType().equals("debit"))  {
                    withdraw(accountId, transaction.getAmount());
                } else {
                    deposit(accountId, transaction.getAmount());
                }
            }
            
            //em.merge(account);
            em.merge(transaction);
            
            em.getTransaction().commit();
        } catch (Exception e) {
            // Log stuff
            return;
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
            Query query = em.createQuery("SELECT t from AccountDB t WHERE t.personKey = :pk");
            query.setParameter("pk", personKey);
            return query.getResultList();
        } catch(Exception e) {
            System.out.println("=======================**************************====================");
            System.out.println("=======================**************************====================");
            System.out.println("=======================**************************====================");
            e.printStackTrace();
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
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            account.setHoldings(account.getHoldings() - amount);
            
            em.merge(account);
            
            em.getTransaction().commit();
            
        } catch(Exception e) {
            //logg stuff
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.close();
        }
        /*
        try {
            Account account = find(id);
            account.setHoldings(account.getHoldings() - amount);
            
        } catch(Exception e) {
            //logg stuff
        }*/
  
    }

    @Override
    public void deposit(long id, long amount) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            account.setHoldings(account.getHoldings() + amount);
            
            em.merge(account);
            
            em.getTransaction().commit();
            
        } catch(Exception e) {
            //logg stuff
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.close();
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

    @Override
    public List<Transaction> findAllTransactions(long accountId) {
        List a = new ArrayList<Transaction>();
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT t from TransactionDB t WHERE t.account.id = :id");
            query.setParameter("id", accountId);
            return query.getResultList();
        } catch(Exception e) {
            System.out.println("=======================**************************====================");
            System.out.println("=======================**************************====================");
            System.out.println("=======================**************************====================");
            e.printStackTrace();
            //log
            return null;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            
            em.close();
        }
        
    }
    
}

