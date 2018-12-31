package com.github.dragonhht.framework.aop.proxy;

/**
 * 代理接口.
 * User: huang
 * Date: 18-12-31
 */
public interface Proxy {

    Object doProxy(ProxyChain chain) throws Throwable;

}
