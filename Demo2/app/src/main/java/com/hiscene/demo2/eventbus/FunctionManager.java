package com.hiscene.demo2.eventbus;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junhu on 2019-10-29
 */
public class FunctionManager {
    private static FunctionManager instance;

    private Map<String, FunctionHasParamHasResult> functionHasParamHasResultMap;
    private Map<String, FunctionHasParamNoResult> functionHasParamNoResultMap;
    private Map<String, FunctionNoParamHasResult> functionNoParamHasResultMap;
    private Map<String, FunctionNoParamNoResult> functionNoParamNoResultMap;

    private FunctionManager() {
        functionHasParamHasResultMap = new HashMap<>();
        functionHasParamNoResultMap = new HashMap<>();
        functionNoParamHasResultMap = new HashMap<>();
        functionNoParamNoResultMap = new HashMap<>();
    }

    public synchronized static FunctionManager getInstance() {
        if (instance == null) {
            instance = new FunctionManager();
        }
        return instance;
    }

    public void addFunction(FunctionHasParamHasResult functionHasParamHasResult) {
        functionHasParamHasResultMap.put(functionHasParamHasResult.funName, functionHasParamHasResult);
    }

    public void addFunction(FunctionHasParamNoResult functionHasParamNoResult) {
        functionHasParamNoResultMap.put(functionHasParamNoResult.funName, functionHasParamNoResult);
    }

    public void addFunction(FunctionNoParamHasResult functionNoParamHasResult) {
        functionNoParamHasResultMap.put(functionNoParamHasResult.funName, functionNoParamHasResult);
    }

    public void addFunction(FunctionNoParamNoResult functionNoParamNoResult) {
        functionNoParamNoResultMap.put(functionNoParamNoResult.funName, functionNoParamNoResult);
    }

    public void invokeFunction(String functionName) {
        if (TextUtils.isEmpty(functionName)) {
            return;
        }

        if (functionNoParamNoResultMap != null) {
            FunctionNoParamNoResult f = functionNoParamNoResultMap.get(functionName);
            if (f != null) {
                f.function();
            } else {
                throw new NullPointerException();
            }
        }
    }

    public <T, P> T invokeFunction(String name, P p, Class<T> t) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        if (functionHasParamHasResultMap != null) {
            FunctionHasParamHasResult f = functionHasParamHasResultMap.get(name);
            if (f != null) {
                if (t != null) {
                    return t.cast(f.function(p));
                }
            } else {
                throw new NullPointerException();
            }
        }
        return null;
    }

    public <P> void invokeFunction(String name, P p) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (functionHasParamNoResultMap != null) {
            FunctionHasParamNoResult f = functionHasParamNoResultMap.get(name);
            if (f != null) {
                f.function(p);
            } else {
                throw new NullPointerException();
            }
        }
    }

    public <T> T invokeFunction(String name, Class<T> t) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        if (functionNoParamHasResultMap != null) {
            FunctionNoParamHasResult f = functionNoParamHasResultMap.get(name);
            if (f != null) {
                if (t != null) {
                    return t.cast(f.function());
                }
            }
        }
        return null;
    }
}
