package com.hiscene.eventbus;

/**
 * Created by junhu on 2019-10-29
 */
public abstract class FunctionHasParamHasResult<T,P> extends Function {
    public FunctionHasParamHasResult(String funName) {
        super(funName);
    }

    public abstract T function(P p);
}
