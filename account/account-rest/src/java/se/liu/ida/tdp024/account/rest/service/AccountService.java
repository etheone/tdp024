package se.liu.ida.tdp024.account.rest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

@Path("/account")
public class AccountService {

    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(), new TransactionEntityFacadeDB());
    private final TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    private final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    @GET
    @Path("create")
    public Response create(
            @QueryParam("name") String name,
            @QueryParam("accounttype") String accountType,
            @QueryParam("bank") String bankName) {
        
        if(name == null || accountType == null || bankName == null) {
            return Response.ok().entity("FAILED" + "").build();
        }
        System.out.println("HEHEHEHEHEHEHEHHEHEHEH");
        
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
        
        //List<Account> allAccounts = accountLogicFacade.findAllAccounts(name);
        String allAccounts = accountLogicFacade.findAllAccounts(name);
        
        System.out.println("###########################");
        System.out.println("### List #####");
        System.out.println(allAccounts);
        
        /*String stringAccounts = "[";
        //List<Account> accounts = new ArrayList<Account>();
        for(Account a : allAccounts) {
            stringAccounts += a;
        }*/
        
        //stringAccounts += "]";
        String json;
        
        
        //json = jsonSerializer.toJson(stringAccounts);
        
        System.out.println("###########################");
        System.out.println("### Result of find in service #####");
        System.out.println(allAccounts);
        System.out.println("###############");
         
        //GenericEntity entity;
        //entity = new GenericEntity<List<Account>>(allAccounts){};
        
        return Response.ok().entity(allAccounts + "").build();
        
    }
    
    @GET
    @Path("debit")
    public Response debit(
            @QueryParam("id") long accountId,
            @QueryParam("amount") long amount) {
        
        String status = transactionLogicFacade.addTransaction(accountId, amount, "debit");
        return Response.ok().entity(status + "").build();
        
    }
    
    @GET
    @Path("credit")
    public Response credit(
            @QueryParam("id") long accountId,
            @QueryParam("amount") long amount) {
        
        String status = transactionLogicFacade.addTransaction(accountId, amount, "credit");
        return Response.ok().entity(status + "").build();
        
    }
    
    @GET
    @Path("transactions")
    public Response findTransactions(
            @QueryParam("id") long accountId) {
        
        String allTransactions = accountLogicFacade.findAllTransactions(accountId);
        //return Response.status(Response.Status.OK).entity(json).build();
        

        System.out.println("==========================================================");
        System.out.println("==== In service ==========================================");
        System.out.println(allTransactions);
        //String json = jsonSerializer.toJson(allTransactions);
        
     
        
        return Response.status(Response.Status.OK).entity(allTransactions+ "").build();
        
    }
    
    
}