/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jp.test.exceptions;

/**
 *
 * @author daniel
 */
public class BusinessException extends Exception {
    public BusinessException(String msg) {
        super(msg);
    }
}
