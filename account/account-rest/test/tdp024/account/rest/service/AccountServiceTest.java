package tdp024.account.rest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.rest.service.AccountService;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.logger.TransactionDTO;

public class AccountServiceTest {

    //-- Units under test ---//
    HTTPHelper httpHelper = new HTTPHelperImpl();
    public static final String ENDPOINT = "http://localhost:8080/AccountDB-rest/";
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();    
    public StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        
        AccountService accountService = new AccountService();

        {
            String name = "Marcus Bendtsen";
            String accountType = "CHECK";
            String bankName = "SWEDBANK";

            Response response = accountService.create(name, accountType, bankName);

            Assert.assertEquals(200, response.getStatus());
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "CREDITCARD";
            Response response = accountService.create(name, accountType, bank);
            
            Assert.assertEquals("FAILED", response.getEntity().toString());
        }
    }
    
    @Test
    public void testFindPerson()
    {
        AccountService accountService = new AccountService();
        
        String name = "Marcus Bendtsen";
        String bank = "SWEDBANK";
        String accountType = "SAVINGS";
        Response response = accountService.create(name, accountType, bank);

        Response res = accountService.find(name);
        
        AccountDB[] allAccountsz = jsonSerializer.fromJson(res.getEntity().toString(), AccountDB[].class);
        System.out.println(allAccountsz[0]);
        List<AccountDB> allAccounts = new ArrayList<AccountDB>();
        for(AccountDB a: allAccountsz) {
            allAccounts.add(a);
        }

        Assert.assertEquals(1, allAccounts.size());
        Assert.assertEquals("SAVINGS", allAccounts.get(0).getAccountType());
    }
    
    @Test
    public void testFindAllTransactions()
    {
        AccountService accountService = new AccountService();
        
        String name = "Marcus Bendtsen";
        String bank = "SWEDBANK";
        String accountType = "SAVINGS";
        Response response = accountService.create(name, accountType, bank);
        
        accountService.credit(1, 200);
        accountService.credit(1, 200);
        accountService.debit(1, 200);
        Response res = accountService.findTransactions(1);
       
        String allTransactionsJson = res.getEntity().toString();
        
        
        TransactionDTO[] allTransactionz = jsonSerializer.fromJson(allTransactionsJson, TransactionDTO[].class);
        List<TransactionDTO> allTransactions = new ArrayList<TransactionDTO>();
        for(TransactionDTO t: allTransactionz) {
            allTransactions.add(t);
        }

        Assert.assertEquals(3, allTransactions.size());
    }
    
    
    
    /*
    @Test
    public void testFindAllTransaction() {
        
        AccountService accountService = new AccountService();
        
        String name = "Marcus Bendtsen";
        String bank = "SWEDBANK";
        String accountType = "SAVINGS";
        accountService.create(name, accountType, bank);
        String accountJson = httpHelper.get(ENDPOINT + "account/find/name", "name", "Marcus Bendtsen");
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AccountServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("###########################");
        System.out.println("### JSON in test-file #####");
        System.out.println(accountJson);
        AccountDB[] accounts = jsonSerializer.fromJson(accountJson, AccountDB[].class);
        
        Response response = accountService.find(name);
        System.out.println(response.getEntity().toString());
        
        httpHelper.get(ENDPOINT + "account/credit", "id", 1 + "", "amount", 200 + "");
        httpHelper.get(ENDPOINT + "account/credit", "id", 1 + "", "amount", 200 + "");
        httpHelper.get(ENDPOINT + "account/debit", "id", 1 + "", "amount", 200 + "");
        
        
        String transactionJson = httpHelper.get(ENDPOINT + "account/transactions", "id", 1 + "");
        System.out.println("********************JSON****************");
        System.out.println(transactionJson);
        TransactionDB[] transactionsArray = jsonSerializer.fromJson(transactionJson, TransactionDB[].class);
        System.out.println(transactionsArray);
        List<Transaction> transactions = new ArrayList<Transaction>();
        for (Transaction t : transactionsArray) {
            transactions.add(t);
        }
        
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t, Transaction t1) {
                if (t.getId() > t1.getId()) {
                    return 1;
                } else if (t.getId() < t1.getId()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        Assert.assertEquals("OK", transactions.get(0).getStatus());
        Assert.assertEquals("OK", transactions.get(1).getStatus());
        Assert.assertEquals("OK", transactions.get(2).getStatus());
        Assert.assertEquals("OK", transactions.get(3).getStatus());
        Assert.assertEquals("OK", transactions.get(4).getStatus());
        Assert.assertEquals("FAILED", transactions.get(5).getStatus());

        Assert.assertEquals(200, transactions.get(0).getAmount());
        Assert.assertEquals(50, transactions.get(1).getAmount());
        Assert.assertEquals(25, transactions.get(2).getAmount());
        Assert.assertEquals(100, transactions.get(3).getAmount());
        Assert.assertEquals(75, transactions.get(4).getAmount());
        Assert.assertEquals(10, transactions.get(5).getAmount());

        Assert.assertEquals("CREDIT", transactions.get(0).getType());
        Assert.assertEquals("DEBIT", transactions.get(1).getType());
        Assert.assertEquals("CREDIT", transactions.get(2).getType());
        Assert.assertEquals("DEBIT", transactions.get(3).getType());
        Assert.assertEquals("DEBIT", transactions.get(4).getType());
        Assert.assertEquals("DEBIT", transactions.get(5).getType());

        /*Assert.assertEquals(AccountDB, transactions.get(0).getAccountDB());
        Assert.assertEquals(AccountDB, transactions.get(1).getAccountDB());
        Assert.assertEquals(AccountDB, transactions.get(2).getAccountDB());
        Assert.assertEquals(AccountDB, transactions.get(3).getAccountDB());
        Assert.assertEquals(AccountDB, transactions.get(4).getAccountDB());
        Assert.assertEquals(AccountDB, transactions.get(5).getAccountDB());*/

        /*Assert.assertNotNull(transactions.get(0).getCreated());
        Assert.assertNotNull(transactions.get(1).getCreated());
        Assert.assertNotNull(transactions.get(2).getCreated());
        Assert.assertNotNull(transactions.get(3).getCreated());
        Assert.assertNotNull(transactions.get(4).getCreated());
        Assert.assertNotNull(transactions.get(5).getCreated());
        
        
    }
    */
    
}