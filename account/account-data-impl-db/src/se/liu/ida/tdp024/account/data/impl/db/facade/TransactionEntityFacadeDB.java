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
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

/**
 *
 * @author emini901, tomli962
 */
public class TransactionEntityFacadeDB implements TransactionEntityFacade{

    private static final AccountLogger accountLogger = new AccountLoggerImpl();
    
    @Override
    public long create(String type, long amount, 
            String created, String status, Account account) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "TransactionEntityFacadeBD.create called.");

        EntityManager em = EMF.getEntityManager();
        try {
            
            em.getTransaction().begin();
            
            Transaction transaction = new TransactionDB();
            transaction.setType(type);
            transaction.setAmount(amount);
            transaction.setCreated(created);
            transaction.setStatus(status);
            transaction.setAccount(account);
            
            em.persist(transaction);
            em.getTransaction().commit();
            
            return transaction.getId();
        } catch (Exception e) {
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to create new transaction in TransactionEntityFacadeBD.create");
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
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "TransactionEntityFacadeBD.find called.");
        EntityManager em = EMF.getEntityManager();
        
        try {
            return em.find(TransactionDB.class, id);
        } catch(Exception e) {
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to find transaction in TransactionEntityFacadeBD.find");            
            return null;
        } finally {
            em.close();
        }
    }
    
}
