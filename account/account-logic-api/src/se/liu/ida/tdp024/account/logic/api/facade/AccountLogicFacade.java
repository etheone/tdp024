package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;

/**
 *
 * @author tomli962, emini901
 */
public interface AccountLogicFacade {
    
    public long create(String personName, String accountType, String bankName);
    
    public Account findByName(String name);
    
    public String findAllAccounts(String name);
    
    public String findAllTransactions(long accountId);
}
