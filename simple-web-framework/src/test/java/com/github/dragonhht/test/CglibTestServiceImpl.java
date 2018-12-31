package com.github.dragonhht.test;

/**
 * 描述.
 * User: huang
 * Date: 18-12-31
 */
public class CglibTestServiceImpl {

    public String hello(String name) {
        System.out.println("hello " + name);
        return name;
    }

}
