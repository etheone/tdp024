package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    
    public long getId();
    
    public void setId(long id);
    
    public String getPersonKey();
    
    public void setPersonKey(String key);
    
    public String getAccountType();
    
    public void setAccountType(String type);
    
    public String getBankKey();
    
    public void setBankKey(String key);
    
    public long getHoldings();
    
    public void setHoldings(long amount);
    
}
