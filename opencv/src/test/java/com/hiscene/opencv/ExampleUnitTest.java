package com.hiscene.opencv;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTestTAG";

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        getAnnotation();
    }

    private void getAnnotation() {
        Class<?> cls = MemberSon.class;

        //获取该类上的所有注解
        Annotation[] annotations = cls.getAnnotations();
        System.out.println( "getAnnotations: " + Arrays.asList(annotations).toString());

        //检查是否有某注解
        boolean present = cls.isAnnotationPresent(Annotation1.class);
        System.out.println("getAnnotation: Annotation1 present :" + present);

        //获取重复注解
        Annotation1[] annotation1 = cls.getAnnotationsByType(Annotation1.class);

        Method[] methods = Member.class.getMethods();
        for (Method method: methods) {
            System.out.println("method : "+method.getName()+",getAnnotation:"+Arrays.asList(method.getAnnotations()).toString());
        }
    }
}