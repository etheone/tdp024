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
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    private static final AccountLogger accountLogger = new AccountLoggerImpl();
    
    @Override
    public long create(String personKey, String accountType, String bankKey) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountEntityFacadeBD.create called.");
        
        EntityManager em = EMF.getEntityManager();
        try {
            
            em.getTransaction().begin();
            
            Account account = new AccountDB();
            account.setPersonKey(personKey);
            account.setBankKey(bankKey);
            account.setAccountType(accountType);
            account.setHoldings(0);
            
            em.persist(account);
            em.getTransaction().commit();
            
            return account.getId();
            
            
        } catch (Exception e) {
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to create new account in AccountEntityFacadeBD.create");
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
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountEntityFacadeBD.addTransaction called.");        
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Account account = em.find(AccountDB.class, accountId, LockModeType.PESSIMISTIC_WRITE);
            
            Transaction transaction = em.find(TransactionDB.class, transactionId);
            
            if(transaction.getType().equals("CREDIT")) {
                deposit(accountId, transaction.getAmount(), account);
            } else {
                if(account.getHoldings() - transaction.getAmount() >= 0) {
                    withdraw(accountId, transaction.getAmount(), account);
                    transaction.setStatus("OK");
                } else {
                    transaction.setStatus("FAILED");
                }
            }
            
            
            em.merge(transaction);
            em.merge(account);
            em.getTransaction().commit();
        } catch (Exception e) {
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to add new transaction in AccountEntityFacadeBD.addTransaction");            
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
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountEntityFacadeBD.find called.");        
        EntityManager em = EMF.getEntityManager();
        
        try {
            return em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_READ);
        } catch(Exception e) {
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to find account in AccountEntityFacadeBD.find");            
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Account> findAll(String personKey) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountEntityFacadeBD.findAll called.");                
        EntityManager em = EMF.getEntityManager();
        try {
            Query query = em.createQuery("SELECT t from AccountDB t WHERE t.personKey = :pk");
            query.setParameter("pk", personKey);
            return query.getResultList();
        } catch(Exception e) {            
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to find all account in AccountEntityFacadeBD.findAll");            
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public String getType(long id) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountEntityFacadeBD.getType called.");   
        try {
            Account account = find(id);
            return account.getAccountType();
        } catch(Exception e) {
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to get type account in AccountEntityFacadeBD.getType");                        
            return null;
        }
    }

    @Override
    public Account withdraw(long id, long amount, Account account) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountEntityFacadeBD.withdraw called.");           
        account.setHoldings(account.getHoldings() - amount);

        return account;
    }

    @Override
    public Account deposit(long id, long amount, Account account) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountEntityFacadeBD.deposit called.");                   
        account.setHoldings(account.getHoldings() + amount);
 
        return account;
    }

    @Override
    public long checkBalance(long id) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountEntityFacadeBD.checkBalance called.");                   
        try {
            Account account = find(id);
            return account.getHoldings();
            
        } catch(Exception e) {
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to check balance in AccountEntityFacadeBD.checkBalance");                        
            return -1;
        }
    }

    @Override
    public List<Transaction> findAllTransactions(long accountId) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountEntityFacadeBD.findAllTransactions called.");
        List a = new ArrayList<Transaction>();
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT t from TransactionDB t WHERE t.account.id = :id");
            query.setParameter("id", accountId);
            return query.getResultList();
        } catch(Exception e) {
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to find all transactions in AccountEntityFacadeBD.findAllTransactions");                        
            return null;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            
            em.close();
        }
        
    }
    
}

