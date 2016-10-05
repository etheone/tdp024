package tdp024.account.rest.service;

import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.rest.service.AccountService;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;

public class AccountServiceTest {

    //-- Units under test ---//
    HTTPHelper httpHelper = new HTTPHelperImpl();
    public static final String ENDPOINT = "http://localhost:8080/account-rest/";
    

    @Test
    public void testCreate() {
        
        AccountService accountService = new AccountService();

        {
            String name = "Tommy Lindman";
            String accountType = "CHECK";
            String bankName = "Bank of Sverige";

            //Response response = accountService.create(name, accountType, bankName);

            //Assert.assertEquals(200, response.getStatus());
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "CREDITCARD";
            Response response = accountService.create(name, accountType, bank);
            System.out.println("The response after bad create:");
            System.out.println(response.getEntity().toString());
            //String response = httpHelper.get(FinalConstants.ENDPOINT + "account/create/", "name", name, "bank", bank, "accounttype", accountType);
            //Assert.assertEquals("FAILED", response);
        }
        {
            String name = "Marcus Bendtsen";
            String bank = "SWEDBANK";
            String accountType = "CREDITCARD";
            
            String response = httpHelper.get(ENDPOINT + "account/create/", "name", name, "bank", bank, "accounttype", accountType);
            System.out.println("Response: " + response);
            if (response.equals("OK"))
                System.out.println("Response is OK...");
            Assert.assertEquals("FAILED", response);
        }
    }
    
}