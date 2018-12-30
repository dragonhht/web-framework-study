package com.github.dragonhht.framework.utils.helper;

import com.github.dragonhht.framework.annotation.Autowired;
import com.github.dragonhht.framework.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * IOC助手类.
 * User: huang
 * Date: 18-12-30
 */
public final class IocHelper {

    static {
        // 获取Bean的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (beanMap != null) {
            // 遍历
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 获取成员属性
                Field[] fields = beanClass.getDeclaredFields();
                if (fields != null) {
                    // 遍历成员属性
                    for (Field field : fields) {
                        // 判断属性是否含有指定的注解
                        if (field.isAnnotationPresent(Autowired.class)) {
                            Class<?> fielsClass = field.getType();
                            Object fieldInstance = beanMap.get(fielsClass);
                            // 注入
                            if (fieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, field, fieldInstance);
                            }
                        }
                    }
                }
            }
        }

    }

}
