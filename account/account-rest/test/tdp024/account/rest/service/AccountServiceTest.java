package tdp024.account.rest.service;

import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.rest.service.AccountService;

public class AccountServiceTest {

    //-- Units under test ---//
    

    @Test
    public void testCreate() {
        
        AccountService accountService = new AccountService();

        String name = "Tommy Lindman";
        String accountType = "CHECK";
        String bankName = "Bank of Sverige";

        Response response = accountService.create(name, accountType, bankName);

        Assert.assertEquals(200, response.getStatus());
    }
    
}