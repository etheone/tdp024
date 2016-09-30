package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountLogicFacade {
    
    public void create(String personName, String accountType, String bankName);
    
    public void addTransaction(long accoundId, long transactionId);
    
    public Account findByName(String name);
}
