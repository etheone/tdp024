package se.liu.ida.tdp024.account.logic.impl.facade;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.ServiceConfigurationError;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private static final AccountLogger accountLogger = new AccountLoggerImpl();
    private final AccountEntityFacade accountEntityFacade;
    //private final TransactionEntityFacade transactionEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "Constructor for AccountLogicFacadeImpl called.");        
        this.accountEntityFacade = accountEntityFacade;
      //  this.transactionEntityFacade = transactionEntityFacade;
    }

    @Override
    public long create(String personName, String accountType, String bankName) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountLogicFacadeImpl.create called.");        
        String personKey = getPersonKey(personName);  
        String bankKey = getBankKey(bankName); 
        if (personKey == null || bankKey == null)
            return -1;
        if (!accountType.equals("CHECK") && !accountType.equals("SAVINGS"))
            return -1;
        long id;
        try {
            id = accountEntityFacade.create(personKey, accountType, bankKey); 
        } catch(ServiceConfigurationError e) {
            accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to create new account in AccountLogicFacadeImpl.create");
            return -1;
        } catch(Exception e) {
             accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to create new account in AccountLogicFacadeImpl.create");
            return -1;
        }
        
        return id;
    }
    
    private String getPersonKey(String name) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountLogicFacadeImpl.getPersonKey called.");
        // Contact our services to get personKey
        HTTPHelper httphelper = new HTTPHelperImpl();
        String response = httphelper.get("http://localhost:8060/person/find.name","name", name);
        
        if (!response.equals("null"))
        {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode actualObj = mapper.readTree(response);
                return actualObj.get("key").toString();
            } catch (IOException e) {
                 accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to get person key in AccountLogicFacadeImpl.getPersonKey");
            }
        }
        return null;
    }
    
    private String getBankKey(String bankName) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountLogicFacadeImpl.getBankKey called.");
        // Contact our services to get bankKey
        HTTPHelper httphelper = new HTTPHelperImpl();
        String response = httphelper.get("http://localhost:8070/bank/find.name","name", bankName);

        if (!response.equals("null"))
        {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode actualObj = mapper.readTree(response);
                return actualObj.get("key").toString();
            } catch (IOException e) {
                 accountLogger.log(AccountLogger.AccountLoggerLevel.ERROR, "Failed to get bank key in AccountLogicFacadeImpl.getBankKey");
            }
        }
        return null;
    } 

    @Override
    public String findAllAccounts(String name) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountLogicFacadeImpl.findAllAccounts called.");
        List<Account> allAccounts = accountEntityFacade.findAll(getPersonKey(name));
        AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
        return jsonSerializer.toJson(allAccounts);
    }

    @Override
    public String findAllTransactions(long accountId) {
        accountLogger.log(AccountLogger.AccountLoggerLevel.DEBUG, "AccountLogicFacadeImpl.findAllTransactions called.");
        List<Transaction> allTransactions = accountEntityFacade.findAllTransactions(accountId);
        AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
        return jsonSerializer.toJson(allTransactions);
    }
    
}
