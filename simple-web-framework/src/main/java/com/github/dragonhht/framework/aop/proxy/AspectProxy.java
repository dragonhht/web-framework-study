package com.github.dragonhht.framework.aop.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 切面代理.
 * User: huang
 * Date: 18-12-31
 */
@Slf4j
public class AspectProxy implements Proxy {


    @Override
    public final Object doProxy(ProxyChain chain) throws Throwable {
        Object result;

        Class<?> cls = chain.getTargetClass();
        Method method = chain.getTargetMethod();
        Object[] params = chain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = chain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = chain.doProxyChain();
            }
        } catch (Exception e) {
            log.error("代理失败");
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    public void begin() {

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) {

    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) {

    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable throwable) {

    }

    public void end() {

    }
}
