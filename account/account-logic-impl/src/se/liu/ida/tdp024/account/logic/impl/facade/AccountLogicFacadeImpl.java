package se.liu.ida.tdp024.account.logic.impl.facade;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.ServiceConfigurationError;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private final AccountEntityFacade accountEntityFacade;
    private final TransactionEntityFacade transactionEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade, TransactionEntityFacade transactionEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
        this.transactionEntityFacade = transactionEntityFacade;
    }

    @Override
    public long create(String personName, String accountType, String bankName) {
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
            System.out.println("Error in logic facade");
            return -1;
        } catch(Exception e) {
            System.out.println("Error in logic facade");
            return -1;
        }
        
        return id;
    }

   /* @Override
    public void addTransaction(long accoundId, long transactionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public Account findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String getPersonKey(String name) {
        // Contact our services to get personKey
        HTTPHelper httphelper = new HTTPHelperImpl();
        String response = httphelper.get("http://localhost:8060/person/find.name","name", name);
        System.out.println();
        System.out.println(response);
        //System.out.println(response.substring(response.indexOf("key")+4, response.indexOf("key")+10));
        System.out.println();
        if (!response.equals("null"))
        {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode actualObj = mapper.readTree(response);
                System.out.println("Hopefully the key below");
                System.out.println(actualObj.get("key"));
                return actualObj.get("key").toString();
            } catch (IOException e) {
                // Log stuff
            }
        }
        return null;
    }
    
    private String getBankKey(String bankName) {
        // Contact our services to get personKey
        HTTPHelper httphelper = new HTTPHelperImpl();
        String response = httphelper.get("http://localhost:8070/bank/find.name","name", bankName);
        System.out.println();
        System.out.println(response);
        //System.out.println(response.substring(response.indexOf("key")+4, response.indexOf("key")+10));
        System.out.println();
        if (!response.equals("null"))
        {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode actualObj = mapper.readTree(response);
                System.out.println("Hopefully the key below");
                System.out.println(actualObj.get("key"));
                return actualObj.get("key").toString();
            } catch (IOException e) {
                // Log stuff
            }
        }
        return null;
    } 

    @Override
    public List<Account> findAllAccounts(String name) {
        List<Account> allAccounts = accountEntityFacade.findAll(getPersonKey(name));
        return allAccounts;
    }

    @Override
    public List<Transaction> findAllTransactions(long accountId) {
        return transactionEntityFacade.findAll(accountId);
    }
    
}
