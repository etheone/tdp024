package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(), new TransactionEntityFacadeDB());
    public TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    public StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
      
        long id = accountLogicFacade.create("Tommy Lindman", "CHECK", "SWEDBANK");
        Assert.assertTrue(id != -1);
    }
    
    @Test
    public void testFindAll() {
        /*
        accountLogicFacade.create("Tommy Lindman", "CHECK", "Bank of Sverige");
        String name = "Tommy Lindman";
        List<Account> allAccounts = accountLogicFacade.findAllAccounts(name);
        
        Assert.assertNotNull(allAccounts);
        Assert.assertEquals(1, allAccounts.size());
        Assert.assertFalse(allAccounts.isEmpty());
        
        accountLogicFacade.create("Tommy Lindman", "SAVINGS", "Bank of Sverige");
        allAccounts = accountLogicFacade.findAllAccounts(name);
        
        Assert.assertNotNull(allAccounts);
        Assert.assertEquals(2, allAccounts.size());
        Assert.assertFalse(allAccounts.isEmpty());
        
        name = "Emil Nilsson";
        allAccounts = accountLogicFacade.findAllAccounts(name);
        
        Assert.assertNotNull(allAccounts);
        Assert.assertEquals(0, allAccounts.size());
        Assert.assertTrue(allAccounts.isEmpty());
        
        
        */
        
    }
    
    @Test
    public void testFindAllTransactions() {
        System.out.println("testFindAllTransactions");
        long accountId = accountLogicFacade.create("Tommy Lindman", "CHECK", "SWEDBANK");
        transactionLogicFacade.addTransaction(accountId, 100, "credit");
        transactionLogicFacade.addTransaction(accountId, 33, "debit");
        transactionLogicFacade.addTransaction(accountId, 31, "debit");
        transactionLogicFacade.addTransaction(accountId, 2, "credit");
        
        String allTransactions = accountLogicFacade.findAllTransactions(accountId);
        
     
        
        String json = accountLogicFacade.findAllAccounts("Tommy Lindman");
        System.out.println(json);
        System.out.println("JSONJSONJSONJSONJSONJSONJSON");
        System.out.println(allTransactions);
        /*List<Account> accounts = 
        Account account = accounts.get(0);
        long amount = account.getHoldings();
        
        Assert.assertEquals(38, amount);
        Assert.assertNotNull(allTransactions);
        Assert.assertEquals(4, allTransactions.size());*/
                
    }
}