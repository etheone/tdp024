package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
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

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    public TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    public StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
      
        long id = accountLogicFacade.create("Marcus Bendtsen", "CHECK", "SWEDBANK");
        Assert.assertTrue(id != -1);
    }
    
    @Test
    public void testFindAllAccounts() {
        
        AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
        accountLogicFacade.create("Marcus Bendtsen", "CHECK", "SWEDBANK");
        String name = "Marcus Bendtsen";
        String accounts = accountLogicFacade.findAllAccounts(name);
        System.out.println(accounts);
        AccountDB[] allAccountsz = jsonSerializer.fromJson(accounts, AccountDB[].class);
        System.out.println(allAccountsz[0]);
        List<AccountDB> allAccounts = new ArrayList<AccountDB>();
        for(AccountDB a: allAccountsz) {
            allAccounts.add(a);
        }
        
        Assert.assertNotNull(allAccounts);
        Assert.assertEquals(1, allAccounts.size());
        Assert.assertFalse(allAccounts.isEmpty());
        
        accountLogicFacade.create("Marcus Bendtsen", "SAVINGS", "SWEDBANK");
        accounts = accountLogicFacade.findAllAccounts(name);
        allAccountsz = jsonSerializer.fromJson(accounts, AccountDB[].class);
        allAccounts = new ArrayList<AccountDB>();
        for(AccountDB a: allAccountsz) {
            allAccounts.add(a);
        }
        Assert.assertNotNull(allAccounts);
        Assert.assertEquals(2, allAccounts.size());
        Assert.assertFalse(allAccounts.isEmpty());
        
        name = "Emil Nilsson";
        accounts = accountLogicFacade.findAllAccounts(name);
        allAccountsz = jsonSerializer.fromJson(accounts, AccountDB[].class);
        allAccounts = new ArrayList<AccountDB>();
        for(AccountDB a: allAccountsz) {
            allAccounts.add(a);
        }
        Assert.assertNotNull(allAccounts);
        Assert.assertEquals(0, allAccounts.size());
        Assert.assertTrue(allAccounts.isEmpty());
        
       
        
    }
    
    @Test
    public void testFindAllTransactions() {
        
        AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
        System.out.println("testFindAllTransactions");
        long accountId = accountLogicFacade.create("Marcus Bendtsen", "CHECK", "SWEDBANK");
        transactionLogicFacade.addTransaction(accountId, 100, "CREDIT");
        transactionLogicFacade.addTransaction(accountId, 33, "DEBIT");
        transactionLogicFacade.addTransaction(accountId, 31, "DEBIT");
        transactionLogicFacade.addTransaction(accountId, 2, "CREDIT");
        
        String allTransactionsz = accountLogicFacade.findAllTransactions(accountId);
        System.out.println(allTransactionsz);
        
        
        TransactionDTO[] allTransactionz = jsonSerializer.fromJson(allTransactionsz, TransactionDTO[].class);
        List<TransactionDTO> allTransactions = new ArrayList<TransactionDTO>();
        for(TransactionDTO t: allTransactionz) {
            allTransactions.add(t);
        }
        Assert.assertNotNull(allTransactions);
        Assert.assertFalse(allTransactions.isEmpty());
        Assert.assertEquals(4, allTransactions.size());
                
    }
}