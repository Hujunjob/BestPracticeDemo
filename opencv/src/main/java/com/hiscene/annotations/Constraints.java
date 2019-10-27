package com.hiscene.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hujun on 2019/4/2.
 *
 */

/**
 * 约束注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Constraints {
    //是否作为主键
    boolean primaryKey() default false;

    //是否唯一
    boolean unique() default false;

    //是否允许为空
    boolean allowNull() default false;
}
