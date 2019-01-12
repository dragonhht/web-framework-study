package com.github.dragonhht.framework.tx.proxy;

import com.github.dragonhht.framework.aop.proxy.Proxy;
import com.github.dragonhht.framework.aop.proxy.ProxyChain;
import com.github.dragonhht.framework.tx.annotation.Transaction;
import com.github.dragonhht.framework.utils.helper.DatabaseHelper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 事务代理.
 * User: huang
 * Date: 19-1-13
 */
@Slf4j
public class TransactionProxy implements Proxy {

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain chain) throws Throwable {
        Object result = null;
        boolean flag = FLAG_HOLDER.get();
        Method method = chain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                log.debug("事务开启");
                result = chain.doProxyChain();
                DatabaseHelper.commitTransaction();
                log.debug("事务提交");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                log.debug("事务回滚");
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = chain.doProxyChain();
        }
        return result;
    }
}
