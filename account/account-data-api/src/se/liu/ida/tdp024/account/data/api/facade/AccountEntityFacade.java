package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface AccountEntityFacade {
    public long create(String personKey, String accountType, String bankKey);
    
    public void addTransaction(long accountId, long transactionId);
    
    public Account find(long id);
    
    public List<Account> findAll(String personKey);
    
    public String getType(long id);
    
    public Account withdraw(long id, long amount, Account account);
    
    public Account deposit(long id, long amount, Account account);
    
    public long checkBalance(long id);
    
    public List<Transaction> findAllTransactions(long accountId);
}
