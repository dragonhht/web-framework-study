package com.github.dragonhht.framework.utils.helper;

import com.github.dragonhht.framework.annotation.Service;
import com.github.dragonhht.framework.aop.annotation.Aspect;
import com.github.dragonhht.framework.aop.proxy.AspectProxy;
import com.github.dragonhht.framework.aop.proxy.Proxy;
import com.github.dragonhht.framework.aop.proxy.ProxyManager;
import com.github.dragonhht.framework.tx.proxy.TransactionProxy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * AOP助手类.
 *
 * @author: huang
 * @Date: 2019-1-1
 */
@Slf4j
public final class AopHelper {

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            log.error("AOP加载失败", e);
        }
    }

    private static Set<Class<?>> createTargetClasses(Aspect aspect) {
        Set<Class<?>> classes = new HashSet<>();
        Class<? extends Annotation> value = aspect.value();
        if (value != null && !value.equals(Aspect.class)) {
            classes.addAll(ClassHelper.getClassesByAnnotation(value));
        }
        return classes;
    }

    // 获取定义增强的类和需要增强的类的Map
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    // 创建需增强的类的实例
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyClasses) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyClasses.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClasses = proxyEntry.getValue();
            for (Class<?> targetClass : targetClasses) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);

                } else {
                    List<Proxy> proxies = new ArrayList<>();
                    proxies.add(proxy);
                    targetMap.put(targetClass, proxies);
                }
            }
        }
        return targetMap;
    }

    // 添加普通切面代理
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyClasses = ClassHelper.getClassesBySyper(AspectProxy.class);
        proxyClasses.forEach(it -> {
            if (it.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = it.getAnnotation(Aspect.class);
                Set<Class<?>> targetClasses = createTargetClasses(aspect);
                proxyMap.put(it, targetClasses);
            }
        });
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClasses = ClassHelper.getClassesByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClasses);
    }
}
