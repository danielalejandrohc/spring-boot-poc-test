/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jp.test.service;

import java.io.IOException;
import java.util.List;
import org.jp.test.exceptions.BusinessException;
import org.jp.test.model.Account;

/**
 *
 * @author daniel
 */
public interface AccountService {
    public List<Account> getAllAccount() throws IOException;
    public String getAllAccountAsJSON() throws IOException; 
    public Account getAccountByName(String name) throws IOException, BusinessException;
    public void create(String jsonAccount) throws IOException;
    public void update(String jsonAccount) throws IOException, BusinessException;
    public void update(Account account) throws IOException, BusinessException;
    public void update(String id, String jsonAccount) throws IOException, BusinessException ;
}
