/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

/**
 *
 * @author emini901, tomli962
 */
public class TransactionLogicFacadeTest {
         //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(), new TransactionEntityFacadeDB());
    public TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    public StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testAddTransaction() {
        long accountId = accountLogicFacade.create("Tommy Lindman", "CHECK", "Bank of Sverige");
        String status = transactionLogicFacade.addTransaction(accountId, 99, "credit");
        String statusFailed = transactionLogicFacade.addTransaction(accountId, 100, "debit");
        System.out.println("************* --------->    " + status);
        Assert.assertTrue(status.equals("OK"));
        Assert.assertTrue(statusFailed.equals("FAILED"));
        
        long accountId2 = accountLogicFacade.create("Charles Xavier", "CHECK", "Jotenheim National Bank");
        String status2 = transactionLogicFacade.addTransaction(accountId2, 100, "credit");
        String status3 = transactionLogicFacade.addTransaction(accountId2, 100, "debit");
        System.out.println("************* --------->    " + status);
        Assert.assertTrue(status2.equals("OK"));
        Assert.assertTrue(status3.equals("OK"));
        
    }
}
