package se.liu.ida.tdp024.account.rest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

@Path("/account")
public class AccountService {

    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(), new TransactionEntityFacadeDB());
    private final TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
   
    @GET
    @Path("create")
    public Response create(
            @QueryParam("name") String name,
            @QueryParam("accounttype") String accountType,
            @QueryParam("bank") String bankName) {
        
        long id = accountLogicFacade.create(name, accountType, bankName);
        System.out.println("************* ----------- id -> " + id);
        if(id != -1) {
            return Response.ok().entity("OK" + "").build();
        } else {
            return Response.ok().entity("FAILED" + "").build();
            //return Response.status(Response.Status.BAD_REQUEST).build();
            
        }
        //return Response.ok().entity(id + "").build();
        
    }
    
    @GET
    @Path("find/name")
    public Response find(
            @QueryParam("name") String name) {
        
        List<Account> allAccounts = accountLogicFacade.findAllAccounts(name);
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        String json;
        try {
            json = objectMapper.writeValueAsString(allAccounts);
        } catch (JsonProcessingException ex) {
            //logg
            return null;
        }
        
        return Response.ok().entity(json + "").build();
        
    }
    
    @GET
    @Path("debit")
    public Response debit(
            @QueryParam("accountId") long accountId,
            @QueryParam("amount") long amount) {
        
        String status = transactionLogicFacade.addTransaction(accountId, amount, "debit");
        return Response.ok().entity(status + "").build();
        
    }
    
    @GET
    @Path("credit")
    public Response credit(
            @QueryParam("accountId") long accountId,
            @QueryParam("amount") long amount) {
        
        String status = transactionLogicFacade.addTransaction(accountId, amount, "credit");
        return Response.ok().entity(status + "").build();
        
    }
    
    @GET
    @Path("transactions")
    public Response findTransactions(
            @QueryParam("accountId") long accountId) {
        
        List<Transaction> allTransactions = accountLogicFacade.findAllTransactions(accountId);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(allTransactions);
        } catch (JsonProcessingException ex) {
            //logg
            return null;
        }
        
        return Response.ok().entity(json + "").build();
        
    }
    
    
}