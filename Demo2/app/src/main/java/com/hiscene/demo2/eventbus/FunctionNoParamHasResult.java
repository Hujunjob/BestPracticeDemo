package com.hiscene.demo2.eventbus;

/**
 * Created by junhu on 2019-10-29
 */
public abstract class FunctionNoParamHasResult<T> extends Function {
    public FunctionNoParamHasResult(String funName) {
        super(funName);
    }

    public abstract T function();
}
