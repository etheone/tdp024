package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface AccountLogicFacade {
    
    public long create(String personName, String accountType, String bankName);
    
    //public void addTransaction(long accoundId, long transactionId);
    
    public Account findByName(String name);
    
    public String findAllAccounts(String name);
    
    public String findAllTransactions(long accountId);
}
