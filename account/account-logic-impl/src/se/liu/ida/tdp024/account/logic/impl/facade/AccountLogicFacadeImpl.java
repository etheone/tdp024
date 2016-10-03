package se.liu.ida.tdp024.account.logic.impl.facade;

import com.fasterxml.jackson.databind.util.JSONPObject;
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
    public void create(String personName, String accountType, String bankName) {
        // Contact our services to get personKey and bankKey
        HTTPHelper httphelper = new HTTPHelperImpl();
        String response = httphelper.get("http://localhost:8060/person/find.name","name", personName);
        System.out.println();
        JSONPObject json = new JSONPObject(response, this);
        System.out.println(response);
        System.out.println(response.substring(response.indexOf("key")+4, response.indexOf("key")+10));
        System.out.println();
        if (!response.equals("null"))
        {
            String personKey = "PKey";
            String bankKey = "BKey";    
            accountEntityFacade.create(personKey, accountType, bankKey);
        }   
    }

    @Override
    public void addTransaction(long accoundId, long transactionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
