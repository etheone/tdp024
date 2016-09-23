package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    public long create(String personKey, String accountType, String bankKey);
    
    public Account find(long id);
    
    public List<Account> findAll(String personKey);
    
    public String getType(long id);
    
    // withdraw and deposit will most likely be removed
    // sinced we added the transaction class
    // Theese two functions are not tested
    public void withdraw(long id, long amount);
    
    public void deposit(long id, long amount);
    
    public long checkBalance(long id);
}
