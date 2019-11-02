package com.hiscene.demo2.eventbus;

/**
 * Created by junhu on 2019-10-29
 */
public abstract class FunctionHasParamNoResult<P> extends Function {
    public FunctionHasParamNoResult(String funName) {
        super(funName);
    }

    public abstract void function(P p);
}
