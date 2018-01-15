package org.jp.test.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jp.test.exceptions.BusinessException;
import org.jp.test.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

/**
 * @author daniel
 */
@Service
public class AccountPatchServiceImpl implements AccountPatchService {

    private final Logger logger = Logger.getLogger(AccountPatchServiceImpl.class.getName());

    @Autowired
    private AccountService accountServcice;

    public void applyUpdatePatch(String customerName, String patch) throws IOException, BusinessException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(patch);
        String operation = jsonNode.get("op").asText();
        String field = jsonNode.get("path").asText().replace("/", "");
        String newValue = jsonNode.get("value").asText();

        if (operation.equals("replace")) {
            Account account = accountServcice.findAccountByName(customerName);
            Field fieldAcc = account.getClass().getDeclaredField(field); //NoSuchFieldException
            fieldAcc.setAccessible(true);
            fieldAcc.set(account, newValue);
            accountServcice.update(account);
        } else {
            throw new BusinessException("Operation " + operation + " not supported");
        }
    }

}
