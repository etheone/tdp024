/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

/**
 *
 * @author emini901, tomli962
 */
public interface Transaction {
    
    public long getId();

    public void setId(long id);
    
    public String getType();
    
    public void setType(String type);
    
    public long getAmount();
    
    public void setAmount(long amount);
    
    public String getDate();
    
    public void setDate(String date);
    
    public String getStatus();
    
    public void setStatus(String status);
    
    public Account getAccount();
    
    public void setAccount(Account account); 
}
