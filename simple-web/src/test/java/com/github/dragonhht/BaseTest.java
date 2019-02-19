package com.github.dragonhht;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 描述.
 * User: huang
 * Date: 18-12-30
 */
public class BaseTest {

    public int testMethod(String name, int age) {
        return 0;
    }


    @Test
    public void testBeanHelper() {
        Class cls = BaseTest.class;
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            if (method.getName().equals("testMethod")) {
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    System.out.println(parameter.getName());
                }
            }
        }
    }

}
