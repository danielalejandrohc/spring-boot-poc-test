package org.jp.test.config;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.jp.test.service.AccountPatchService;
import org.jp.test.service.AccountPatchServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.jp.test.rest.v1.accounts.AccountRestService;
import org.jp.test.service.AccountService;
import org.jp.test.service.AccountServiceImpl;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @author daniel
 */
@Configuration
@ApplicationPath("rest")
public class JerseyConfiguration extends ResourceConfig {

    // JSON file with some accounts
    public static final String ACCOUNT_FILE = "accounts.json";

    public JerseyConfiguration() {

    }

    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl();
    }

    @Bean
    public AccountPatchService accountPatchService() {
        return new AccountPatchServiceImpl();
    }
    
    /**
     * Setup the services that we will provide
     */
    @PostConstruct
    public void setUp() {
        register(GenericExceptionMapper.class);
        register(AccountRestService.class);
    }
}
