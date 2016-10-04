package se.liu.ida.tdp024.account.logic.impl.facade;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.io.IOException;
import java.util.ServiceConfigurationError;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }

    @Override
    public long create(String personName, String accountType, String bankName) {
        String personKey = getPersonKey(personName);  
        String bankKey = getBankKey(bankName); 
        long id;
        try {
            id = accountEntityFacade.create(personKey, accountType, bankKey); 
        } catch(ServiceConfigurationError e) {
            return -1;
        } catch(Exception e) {
            return -1;
        }
        
        return id;
    }

    @Override
    public void addTransaction(long accoundId, long transactionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
    
}
