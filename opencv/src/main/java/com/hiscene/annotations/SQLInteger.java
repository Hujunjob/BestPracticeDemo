package com.hiscene.annotations;

/**
 * Created by hujun on 2019/4/2.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解Integer类型的字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SQLInteger {
    //对应数据库表的列名
    String name() default "";

    //列类型分配的长度，例如varchar(30)的30
    int value() default 0;

    //约束条件
    Constraints constraint() default @Constraints;
}
