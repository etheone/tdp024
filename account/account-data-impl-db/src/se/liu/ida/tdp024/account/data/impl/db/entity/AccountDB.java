package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

@Entity
public class AccountDB implements Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String personKey;
    private String bankKey;
    private String accountType;
    private long holdings;
    
    @OneToMany(mappedBy = "account", targetEntity = TransactionDB.class)
    private List<Transaction> transactions;
    
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
    
    @Override
    public List<Transaction> getTransactions()
    {
        return this.transactions;
    }
    
    @Override
    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }
    
    @Override
    public void addTransactionToAccount(Transaction t) {
        this.transactions.add(t);
    }
}
