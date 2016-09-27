package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author emini901, tomli962
 */
public interface TransactionEntityFacade {
    public long create(String type, long amount,
            String date, String status, Account account);
    
    public Transaction find(long id);
    
    public List<Transaction> findAll(long id);
}
