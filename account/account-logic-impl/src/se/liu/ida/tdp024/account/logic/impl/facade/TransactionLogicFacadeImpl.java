package se.liu.ida.tdp024.account.logic.impl.facade;

import java.text.SimpleDateFormat;
import java.util.Date;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

/**
 *
 * @author emini901, tomli962
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {

    private static final AccountLogger accountLogger = new AccountLoggerImpl();
    private final TransactionEntityFacade transactionEntityFacade;
    
    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Constructor for TransactionLogicFacadeImpl called.");                
        this.transactionEntityFacade = transactionEntityFacade;
    }
    
  /*  @Override
    public long create(String type, long amount, String date, String status, Account account) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "TransactionLogicFacadeImpl.create called.");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public String addTransaction(long accountId, long amount, String type) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "TransactionLogicFacadeImpl.addTransaction called.");
        
        AccountEntityFacade accountEntityFacadeDB = new AccountEntityFacadeDB();
        Account account = accountEntityFacadeDB.find(accountId);
        
        if (account != null) {
            String status = null;
            if (type.equals("CREDIT")) {
                status = "OK";
            }
            
            //TransactionEntityFacade transactionEntityFacadeDB = new TransactionEntityFacadeDB();
            Date created = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            try {
                long transId = transactionEntityFacade.create(type, amount, dateFormat.format(created), status, account);         
                accountEntityFacadeDB.addTransaction(accountId, transId);
                return status;
            } catch(Exception e) {
                accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to add transaction in TransactionLogicFacadeImpl.addTransaction.");
                
                return e.getStackTrace().toString();
            }
            
            
        } else {
            return "FAILED";
        }
        
    }

/*    @Override
    public Transaction find(long id) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "TransactionLogicFacadeImpl.find called.");
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */
}
