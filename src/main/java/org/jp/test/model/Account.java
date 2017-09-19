/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jp.test.model;

/**
 *
 * @author daniel
 */
public class Account {
    
    private String amount;
    private String currency;
    private String customerName;

    public Account() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Account{" + "amount=" + amount + ", currency=" + currency + ", customerName=" + customerName + '}';
    }
    
    
}
