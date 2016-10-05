package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

/**
 *
 * @author tomli962, emini901
 */
public class TransactionEntityFacadeTest {
    
    //---- Unit under test ----//
    private TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        long accountId = accountEntityFacade.create("PKey", "TYPE", "BKey");
        Account account = accountEntityFacade.find(accountId);
        long id = transactionEntityFacade.create("test", 5, "2015", "ok", account);
        Assert.assertFalse(id == 0);
    }
    
    @Test
    public void testFindAllTransactions() {
        long accountId = accountEntityFacade.create("PKey", "TYPE", "BKey");
        Account account = accountEntityFacade.find(accountId);
        transactionEntityFacade.create("test", 5, "2015", "ok", account);
        transactionEntityFacade.create("test", 5, "2015", "ok", account);
        transactionEntityFacade.create("test", 5, "2015", "ok", account);
        transactionEntityFacade.create("test", 5, "2015", "ok", account);
        List<Transaction> transList = accountEntityFacade.findAllTransactions(accountId);
        System.out.println(transList);
        System.out.println("Translist above");
        for(Transaction t : transList) {
            System.out.println(t.getStatus());
        }
        
        Assert.assertFalse(transList.isEmpty());
 
        
    }
    
}
