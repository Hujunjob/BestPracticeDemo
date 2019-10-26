package com.hiscene.retrofit;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by junhu on 2019-10-23
 */
@Documented
@Retention(RUNTIME)
public @interface GET {
    String value();
}
