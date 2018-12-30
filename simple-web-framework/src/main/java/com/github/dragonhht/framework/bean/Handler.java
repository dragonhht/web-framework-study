package com.github.dragonhht.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * 用于封装请求处理信息.
 * User: huang
 * Date: 18-12-30
 */
@Getter
@Setter
@AllArgsConstructor
public class Handler {

    /** Controller类。 */
    private Class<?> controllerClass;
    /** 处理方法. */
    private Method actionMethod;

}
