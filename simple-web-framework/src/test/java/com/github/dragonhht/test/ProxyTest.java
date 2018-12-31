package com.github.dragonhht.test;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * 描述.
 * User: huang
 * Date: 18-12-31
 */
public class ProxyTest {

    @Test
    public void testJdkProxy() {
        TestService testService = new TestServiceImpl();
        JdkProxy proxy = new JdkProxy(testService);
        TestService service = (TestService) Proxy.newProxyInstance(testService.getClass().getClassLoader(),
                testService.getClass().getInterfaces(), proxy);
        service.print("dragonhht");
    }

    @Test
    public void testCglibProxy() {
        CglibProxy proxy = new CglibProxy();
        CglibTestServiceImpl service = proxy.getProxy(CglibTestServiceImpl.class);
        System.out.println(service.hello("dragonhht"));
    }

}
