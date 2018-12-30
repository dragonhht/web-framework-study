package com.github.dragonhht.framework.utils.helper;

import com.github.dragonhht.framework.utils.ClassUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 加载相关的Helper类.
 * User: huang
 * Date: 18-12-30
 */
@Slf4j
public final class Helperloader {

    /**
     * 初始化.
     */
    public static void init() {
        try {
            Class<?>[] classes = {
                    ClassHelper.class,
                    BeanHelper.class,
                    IocHelper.class,
                    ControllerHelper.class
            };
            for (Class<?> clas : classes) {
                ClassUtil.loadClass(clas.getName(), true);
            }
        } catch (ClassNotFoundException e) {
            log.error("初始化Helper失败", e);
        }
    }

}
