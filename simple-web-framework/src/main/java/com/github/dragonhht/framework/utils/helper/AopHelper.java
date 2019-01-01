package com.github.dragonhht.framework.utils.helper;

import com.github.dragonhht.framework.aop.annotation.Aspect;
import com.github.dragonhht.framework.aop.proxy.AspectProxy;
import com.github.dragonhht.framework.aop.proxy.Proxy;
import com.github.dragonhht.framework.aop.proxy.ProxyManager;
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
        Set<Class<?>> proxyClasses = ClassHelper.getClassesBySyper(AspectProxy.class);
        for (Class<?> cls : proxyClasses) {
            if (cls.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = cls.getAnnotation(Aspect.class);
                Set<Class<?>> targetClasses = createTargetClasses(aspect);
                proxyMap.put(cls, targetClasses);
            }
        }
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

}
