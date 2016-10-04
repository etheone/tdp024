package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author tomli962, emini901
 */
public interface TransactionLogicFacade {
    
    public long create(String type, long amount, 
            String date, String status, Account account);
    
    public String addTransaction(long accoundId, long amount, String type);
    
    public Transaction find(long id);
    
}
