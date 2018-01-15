/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jp.test.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jp.test.exceptions.BusinessException;
import org.jp.test.model.Account;
import org.jp.test.rest.v1.accounts.AccountRestService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author daniel
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = Logger.getLogger(AccountRestService.class.getName());

    /**
     * Reall the json file and convert it to an array of objects
     *
     * @return
     * @throws IOException
     */
    @Override
    public List<Account> getAllAccount() throws IOException {
        String accountsJson = getAllAccountAsJSON();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Account> ret = null;
        if(accountsJson == null || accountsJson.trim().isEmpty()) {
            ret = new ArrayList<>();
        } else {
            ret = objectMapper.readValue(accountsJson, new TypeReference<List<Account>>() {
            });
        }

        return ret;
    }

    /**
     * Retrieves from the accounts the first occurs of 'custonerName' provided  
     * @param customerName
     * @return
     * @throws IOException 
     */
    public Account findAccountByName(String customerName) throws IOException, BusinessException {
        List<Account> allAccount = getAllAccount();

        Optional<Account> firstAccount = allAccount.stream()
                .filter(acc -> acc.getCustomerName().equals(customerName))
                .findFirst();

        if (firstAccount.isPresent()) {
            return firstAccount.get();
        } else {
            throw new BusinessException(customerName + " not found");
        }
    }

    /**
     * Retrieve the first account found it base on the customerName
     *
     * @param name
     * @return
     * @throws IOException
     * @throws BusinessException
     */
    @Override
    public Account getAccountByName(String name) throws IOException, BusinessException {
        List<Account> accounts = getAllAccount();
        Optional<Account> account = accounts.stream().filter(acc -> acc.getCustomerName().equals(name)).findFirst();
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new BusinessException("Account not found");
        }
    }

    /**
     * Retrieves the Accotuns JSON file as it is
     *
     * @return
     * @throws IOException
     */
    @Override
    public String getAllAccountAsJSON() throws IOException {
        java.nio.file.Path path = Paths.get(System.getProperty("java.io.tmpdir") + "/accounts.json");//new File(classLoader.getResource(JerseyConfiguration.ACCOUNT_FILE).getFile()).toPath();
        String content = null;
        if(path.toFile().exists()) {
            content = new String(Files.readAllBytes(path));
        }
        return content;
    }

    /**
     * Pase the accounts JSON file to a list of objects then add the new object
     * to the list and write the list to the file
     *
     * @param jsonAccount
     * @throws IOException
     */
    @Override
    public void create(String jsonAccount) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = objectMapper.readValue(jsonAccount, Account.class);
        List<Account> allAccount = getAllAccount();
        allAccount.add(account);
        submitToFile(allAccount);
        logger.log(Level.INFO, "Account created {0}", jsonAccount);
    }

    /**
     * Base on an CustomerName (Id) it updates all the matches with that
     * customerName with the new account info
     *
     * @param id
     * @param jsonAccount
     * @throws IOException
     * @throws BusinessException
     */
    @Override
    public void update(String id, String jsonAccount) throws IOException, BusinessException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = objectMapper.readValue(jsonAccount, Account.class);

        if (account.getCustomerName().equals(id)) {
            update(account);
        } else {
            throw new BusinessException("Id: " + id + " , does not match with customerName in payload " + jsonAccount);
        }
    }

    /**
     * Find a customerName contained in the JSON file and then update the object
     *
     * @param jsonAccount
     * @throws IOException
     * @throws BusinessException
     */
    @Override
    public void update(String jsonAccount) throws IOException, BusinessException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = objectMapper.readValue(jsonAccount, Account.class);
        update(account);
    }

    /**
     * Fin the Account in the List and update it with new info, if it is not
     * present then throw an Exception
     *
     * @param account
     * @throws IOException
     * @throws BusinessException
     */
    @Override
    public void update(Account account) throws IOException, BusinessException {
        List<Account> allAccount = getAllAccount();
        AtomicInteger atomicInteger = new AtomicInteger();

        allAccount.stream()
                .filter(acc -> acc.getCustomerName().equals(account.getCustomerName()))
                .forEach(acc -> {
                    acc.setAmount(account.getAmount());
                    acc.setCurrency(account.getCurrency());
                    atomicInteger.incrementAndGet();
                });

        if (atomicInteger.get() == 0) {
            throw new BusinessException("Account " + account + " not found");
        }

        submitToFile(allAccount);
    }

    /**
     * Overwrite a file with List of accouns provided
     *
     * @param accounts
     * @throws IOException
     */
    private void submitToFile(List<Account> accounts) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // To prettify the output json file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ClassLoader classLoader = this.getClass().getClassLoader();
        Path tempPath = Paths.get(System.getProperty("java.io.tmpdir") + "/accounts.json");
        File file = tempPath.toFile();//new File(classLoader.getResource(JerseyConfiguration.ACCOUNT_FILE).getFile());
        objectMapper.writeValue(file, accounts);
        logger.log(Level.INFO, "File created/updated {0} @ {1}", new Object[]{accounts, file.toString()});
    }

}
