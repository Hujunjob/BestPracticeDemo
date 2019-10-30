package com.hiscene.eventbus;

/**
 * Created by junhu on 2019-10-29
 */
public abstract class FunctionNoParamNoResult extends Function {

    public FunctionNoParamNoResult(String funName) {
        super(funName);
    }

    public abstract void function();
}
