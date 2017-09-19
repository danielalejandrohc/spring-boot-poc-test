package org.jp.test.service;

import org.jp.test.exceptions.BusinessException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface AccountPatchService {
    public void applyUpdatePatch(String customerName, String patch) throws IOException, BusinessException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException;
}
