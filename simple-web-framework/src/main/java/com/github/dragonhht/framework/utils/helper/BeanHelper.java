package com.github.dragonhht.framework.utils.helper;

import com.github.dragonhht.framework.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean的助手类.
 * User: huang
 * Date: 18-12-30
 */
@Slf4j
public final class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        // 将基础包下的类放入容器
        Set<Class<?>> classes = ClassHelper.getClasses();
        classes.forEach(it -> {
            Object obj = ReflectionUtil.newInstance(it);
            BEAN_MAP.put(it, obj);
        });
    }

    /**
     * 获取Bean的映射.
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例.
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("获取的类实例不存在!");
        }
        return (T) BEAN_MAP.get(cls);
    }

}
