package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import se.liu.ida.tdp024.account.data.api.entity.Account;


/**
 *
 * @author emini901, tomli962
 */
@Entity
public class AccountDB implements Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String personKey;
    private String bankKey;
    private String accountType;
    private long holdings;
    
    
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getPersonKey() {
        return personKey;
    }

    @Override
    public void setPersonKey(String key) {
        this.personKey = key;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }
    @Override
    public void setAccountType(String type) {
        this.accountType = type;
    }

    @Override
    public String getBankKey() {
        return bankKey;
    }

    @Override
    public void setBankKey(String key) {
        this.bankKey = key;
    }

    @Override
    public long getHoldings() {
        return holdings;
    }

    @Override
    public void setHoldings(long amount) {
        this.holdings = amount;
    }
    
}
