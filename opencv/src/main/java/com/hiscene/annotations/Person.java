package com.hiscene.annotations;

/**
 * Created by hujun on 2019/4/2.
 */
@DBTable(name = "PERSON")
public class Person {
    @SQLString(name = "ID",value = 50,constraint = @Constraints(primaryKey = true,unique = true))
    private String id;

    @SQLString(name = "NAME",value = 30,constraint = @Constraints(unique = true))
    private String name;

    @SQLInteger(name = "AGE")
    private int age;

    @SQLString(name = "INFO",value = 150,constraint = @Constraints(allowNull = true))
    private String info;
}
