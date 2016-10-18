/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.ArrayList;
import java.util.List;
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
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.logger.TransactionDTO;

/**
 *
 * @author emini901, tomli962
 */
public class TransactionLogicFacadeTest {
         //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    public TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    public StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testAddTransaction() {
        AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
        long accountId = accountLogicFacade.create("Marcus Bendtsen", "CHECK", "SWEDBANK");
        transactionLogicFacade.addTransaction(accountId, 99, "CREDIT");
        transactionLogicFacade.addTransaction(accountId, 100, "DEBIT");
        
       
        String allTransactionsz = accountLogicFacade.findAllTransactions(accountId);
        System.out.println(allTransactionsz);
        
        
        TransactionDTO[] allTransactionz = jsonSerializer.fromJson(allTransactionsz, TransactionDTO[].class);
        List<TransactionDTO> allTransactions = new ArrayList<TransactionDTO>();
        for(TransactionDTO t: allTransactionz) {
            allTransactions.add(t);
        }
        
        
        //System.out.println("************* --------->    " + status);
        Assert.assertTrue(allTransactions.get(0).getStatus().equals("OK"));
        Assert.assertTrue(allTransactions.get(1).getStatus().equals("FAILED"));
        
        
        long accountId2 = accountLogicFacade.create("Marcus Bendtsen", "CHECK", "SWEDBANK");
        transactionLogicFacade.addTransaction(accountId2, 100, "CREDIT");
        transactionLogicFacade.addTransaction(accountId2, 100, "DEBIT");
        allTransactionsz = accountLogicFacade.findAllTransactions(accountId2);
        System.out.println(allTransactionsz);
        
        
        allTransactionz = jsonSerializer.fromJson(allTransactionsz, TransactionDTO[].class);
        allTransactions = new ArrayList<TransactionDTO>();
        for(TransactionDTO t: allTransactionz) {
            allTransactions.add(t);
        }
        
        
        //System.out.println("************* --------->    " + status);
        Assert.assertTrue(allTransactions.get(0).getStatus().equals("OK"));
        Assert.assertTrue(allTransactions.get(1).getStatus().equals("OK"));

        
    }
}
