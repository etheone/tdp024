package se.liu.ida.tdp024.account.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

@Path("/account")
public class AccountService {

    private static final AccountLogger accountLogger = new AccountLoggerImpl();
    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(), new TransactionEntityFacadeDB());
    private final TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    private final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    
    @GET
    @Path("create")
    public Response create(
            @QueryParam("name") String name,
            @QueryParam("accounttype") String accountType,
            @QueryParam("bank") String bankName) {
        
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountService.create called.");        
        if(name == null || accountType == null || bankName == null) {
            return Response.ok().entity("FAILED" + "").build();
        }
        
        long id = accountLogicFacade.create(name, accountType, bankName);
        if(id != -1) {
            return Response.ok().entity("OK" + "").build();
        } else {
            return Response.ok().entity("FAILED" + "").build();
            
        
        }
        
    }
    
    @GET
    @Path("find/name")
    public Response find(
            @QueryParam("name") String name) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountService.find called.");
        
        String allAccounts = accountLogicFacade.findAllAccounts(name);

        return Response.ok().entity(allAccounts + "").build();
        
    }
    
    @GET
    @Path("debit")
    public Response debit(
            @QueryParam("id") long accountId,
            @QueryParam("amount") long amount) {
         accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountService.debit called.");
         
        String status = transactionLogicFacade.addTransaction(accountId, amount, "DEBIT");
        return Response.ok().entity(status + "").build();
        
    }
    
    @GET
    @Path("credit")
    public Response credit(
            @QueryParam("id") long accountId,
            @QueryParam("amount") long amount) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountService.credit called.");
         
        String status = transactionLogicFacade.addTransaction(accountId, amount, "CREDIT");
        return Response.ok().entity(status + "").build();
        
    }
    
    @GET
    @Path("transactions")
    public Response findTransactions(
            @QueryParam("id") long accountId) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountService.findAllTransactions called.");
        String allTransactions = accountLogicFacade.findAllTransactions(accountId);
        
        
        return Response.status(Response.Status.OK).entity(allTransactions+ "").build();
        
    }
    
    
}