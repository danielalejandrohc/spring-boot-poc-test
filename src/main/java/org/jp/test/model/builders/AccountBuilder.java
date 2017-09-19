/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jp.test.model.builders;

import org.jp.test.model.Account;

/**
 *
 * @author daniel.hernandez01
 */
public class AccountBuilder {
    private String amount;
    private String currency;
    private String customerName; 
    
    public AccountBuilder(String customerName) {
        this.customerName = customerName;
    }
    
    public AccountBuilder(Account account) {
        this.amount = account.getAmount();
        this.currency = account.getCurrency();
        this.customerName = account.getCustomerName();
    }

    public String getAmount() {
        return amount;
    }

    public AccountBuilder setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public AccountBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public AccountBuilder setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }
    
    public Account build() {
        Account acc = new Account();
        acc.setAmount(amount);
        acc.setCurrency(currency);
        acc.setCustomerName(customerName);
        return acc;
    }
    
}
