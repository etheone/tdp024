package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        long id = accountEntityFacade.create("PERSON1_KEY", "ACCOUNT_TYPE", "BANK_KEY");
        Assert.assertFalse(id == 0);
    }
    
    @Test
    public void testFind() {
        long id = accountEntityFacade.create("PERSON1_KEY", "ACCOUNT_TYPE", "BANK_KEY");
        Account account = accountEntityFacade.find(id);
        Assert.assertNotNull(account);
        Assert.assertEquals("PERSON1_KEY", account.getPersonKey());
    }
    
    @Test
    public void testFindAll() {
        long id1 = accountEntityFacade.create("PERSON1_KEY", "CHECK", "BANK_KEY1");
        long id2 = accountEntityFacade.create("PERSON1_KEY", "SAVINGS", "BANK_KEY1");
        List<Account> allAccounts = accountEntityFacade.findAll("PERSON1_KEY");
        Assert.assertNotNull(allAccounts);
        Assert.assertEquals(2, allAccounts.size());
    }
    
    @Test
    public void testGetType() {
        final String TYPE1 = "CHECK";
        long id1 = accountEntityFacade.create("PERSON1_KEY", TYPE1, "BANK_KEY1");
        String type1 = accountEntityFacade.getType(id1);
        Assert.assertNotNull(type1);
        Assert.assertEquals(TYPE1, type1);
        final String TYPE2 = "SAVINGS";
        long id2 = accountEntityFacade.create("PERSON1_KEY", TYPE2, "BANK_KEY1");
        String type2 = accountEntityFacade.getType(id2);
        Assert.assertNotNull(type2);
        Assert.assertEquals(TYPE2, type2);
    }
    
    @Test
    public void testCheckBalance() {
        long id1 = accountEntityFacade.create("PERSON1_KEY", "CHECK", "BANK_KEY1");
        long savings = accountEntityFacade.checkBalance(id1);
        Assert.assertNotNull(savings);
        Assert.assertEquals(0, savings);
        //accountEntityFacade.deposit(id1, 50);
        savings = accountEntityFacade.checkBalance(id1);
        Assert.assertNotNull(savings);
        Assert.assertEquals(50, savings);
        // Testing to withdraw
        //accountEntityFacade.withdraw(id1, 30);
        savings = accountEntityFacade.checkBalance(id1);
        Assert.assertEquals(20, savings);
    }
}