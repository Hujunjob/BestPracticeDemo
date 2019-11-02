package com.hiscene.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by junhu on 2019-10-25
 */
public class PersonInvocation {
    private Person person;


    public PersonInvocation(Person person) {
        this.person = person;
    }

    public Person bind(){
        return (Person) Proxy.newProxyInstance(person.getClass().getClassLoader(), person.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName()+" before");
                Object res = method.invoke(person,args);
                System.out.println(method.getName()+" after");
                return res;
            }
        });
    }
}
