package com.github.dragonhht.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由注解.
 *
 * @author: huang
 * @Date: 2018-12-28
 */
// TODO 暂时支持GET请求
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * 路径
     * @return
     */
    String value();

}
