package com.github.dragonhht.test;

/**
 * 动态代理测试中测试代理的类.
 * User: huang
 * Date: 18-12-31
 */
public class TestServiceImpl implements TestService {

    @Override
    public void print(String name) {
        System.out.println(name + "Proxy Test");
    }

}
