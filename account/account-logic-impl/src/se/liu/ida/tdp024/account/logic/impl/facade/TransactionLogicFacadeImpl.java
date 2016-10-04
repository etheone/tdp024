/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.Date;
import javax.net.ssl.SSLEngineResult;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;

/**
 *
 * @author emini901, tomli962
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {

    private TransactionEntityFacade transactionEntityFacade;
    
    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
    }
    
    @Override
    public long create(String type, long amount, String date, String status, Account account) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String addTransaction(long accountId, long amount, String type) {
        
        AccountEntityFacade accountEntityFacadeDB = new AccountEntityFacadeDB();
        Account account = accountEntityFacadeDB.find(accountId);
        
        if (account != null) {
            
            String status = "FAILED";
            
            if (type == "credit") {
                status = "OK";
            } else if((account.getHoldings() - amount) >= 0) {
                status = "OK";
            }
            
            TransactionEntityFacade transactionEntityFacadeDB = new TransactionEntityFacadeDB();
            Date date = new Date();
            
            try {
                long transId = transactionEntityFacadeDB.create(type, amount, date.toString(), status, account);
                accountEntityFacadeDB.addTransaction(account.getId(), transId);
            } catch(Exception e) {
                //trans was not creator or not added to account
            }
            
            return status;
        } else {
            return "";
        }
        
    }

    @Override
    public Transaction find(long id) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}