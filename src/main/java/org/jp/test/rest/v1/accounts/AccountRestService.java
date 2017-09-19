/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jp.test.rest.v1.accounts;

import java.io.IOException;
import java.util.logging.Level;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import org.jp.test.exceptions.BusinessException;
import org.jp.test.interfaces.PATCH;
import org.jp.test.model.Account;
import org.jp.test.service.AccountPatchService;
import org.jp.test.service.AccountPatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.jp.test.service.AccountService;

import java.util.logging.Logger;
import org.jp.test.config.JerseyConfiguration;

/**
 * @author daniel.hernandez01
 */
@Component
@Path("/v1/accounts")
public class AccountRestService {

    private final Logger logger = Logger.getLogger(AccountRestService.class.getName());

    @Autowired
    private AccountService service;

    @Autowired
    private AccountPatchService patchService;

    /*
        /v1/accounts/account/account_id      // to get details of an account, GET
     */
    @Path("account/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetails(@PathParam("id") String id) {
        try {
            Account account = service.getAccountByName(id);
            Response response = Response.status(JerseyConfiguration.SUCCESS_REPONSE).entity(account).build();
            return response;
        } catch (IOException | BusinessException ex) {
            logger.log(Level.SEVERE, null, ex);
            Response response = Response.status(JerseyConfiguration.ERROR_REPONSE).entity(ex).build();
            return response;
        }
    }

    /*
        /v1/accounts/account     // to create an account, JSON input, POST
     */
    @Path("account")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String accountJson) {
        try {
            logger.log(Level.INFO, "create {0}", accountJson);
            service.create(accountJson);
            Response response = Response.status(JerseyConfiguration.SUCCESS_REPONSE).entity(JerseyConfiguration.OK_JSON_RESPONSE).build();
            logger.log(Level.INFO, "acc. created");
            return response;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            Response response = Response.status(JerseyConfiguration.ERROR_REPONSE).entity(ex).build();
            return response;
        }
    }

    /*
        /v1/accounts/account/account_id      // to update an account, PATCH
        Example of payload:
        [
            { "op": "replace", "path": "/currency", "value": "CDN" }
        ]
     */
    @Path("account/{id}")
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, String patch) {
        logger.log(Level.INFO, "update id: {0}, json request: {1}", new Object[]{id, patch});
        try {
            logger.log(Level.INFO, "update using patch");
            patchService.applyUpdatePatch(id, patch);
            Response response = Response.status(JerseyConfiguration.SUCCESS_REPONSE).entity(JerseyConfiguration.OK_JSON_RESPONSE).build();
            return response;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AccountRestService.class.getName()).log(Level.SEVERE, null, ex);
            Response response = Response.status(JerseyConfiguration.ERROR_REPONSE).entity(ex).build();
            return response;
        }
    }

    
    /*
        /v1/accounts/account         // list all the account, GET
     */
    @Path("account")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
        try {
            String accounts = service.getAllAccountAsJSON();
            Response response = Response.status(JerseyConfiguration.SUCCESS_REPONSE).entity(accounts).build();
            return response;
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AccountRestService.class.getName()).log(Level.SEVERE, null, ex);
            Response response = Response.status(JerseyConfiguration.ERROR_REPONSE).entity(ex).build();
            return response;
        }
    }
}
