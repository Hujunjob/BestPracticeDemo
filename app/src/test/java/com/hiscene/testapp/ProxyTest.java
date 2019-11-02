package com.hiscene.testapp;

import com.hiscene.proxy.Person;
import com.hiscene.proxy.PersonInvocation;
import com.hiscene.proxy.Student;
import com.hiscene.retrofit.GET;
import com.hiscene.retrofit.ProxyStudy;
import com.hiscene.retrofit.Query;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by junhu on 2019-10-23
 */
public class ProxyTest {

    interface HOST{
        @GET("/user")
        void login(@Query("id") String id);
    }

    @Test
    public void proxy(){
        Class clz = HOST.class;
        HOST host = (HOST) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println(method.getName());

//                System.out.println(args[0]);

                String methodType = "";


                Annotation[] annotation = method.getAnnotations();
                for (Annotation annotation1 : annotation) {
                    if (annotation1 instanceof GET){
                        methodType = "GET";
                        System.out.println(methodType+((GET)annotation1).value());
                        break;
                    }
                }


                return null;
            }
        });
        host.login("1");
    }

    @Test
    public void proxyTest(){
        ProxyStudy proxyStudy = new ProxyStudy();
        proxyStudy.test();
    }

    @Test
    public void proxyTest2(){
        Person person = new Student();
        PersonInvocation personInvocation = new PersonInvocation(person);
        Person p = personInvocation.bind();
        p.say("hello");
        boolean res = p.sleep();
        System.out.println(res);
    }
}
