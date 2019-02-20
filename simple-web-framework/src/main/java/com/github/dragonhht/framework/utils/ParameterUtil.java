package com.github.dragonhht.framework.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

/**
 * 方法参数工具类.
 *
 * @author: huang
 * @Date: 2019-2-19
 */
public final class ParameterUtil {

    /**
     * 填充实体属性值.
     * @param obj 对象实例
     * @param name 属性名
     * @param value 属性值
     * @param <T> 对象实例类型
     * @return 填充属性的对象实例
     */
    public static <T> T stuffValue(T obj, String name, String value) {
        Class<?> cla = obj.getClass();
        Method method = getSetter(name, cla);
        if (method == null) {
            throw new RuntimeException("未在类: " + cla.getName() + " 中找到指定的Setter方法");
        }
        ReflectionUtil.invokeMethod(obj, method, value);
        return obj;
    }

    /**
     * 获取属性的Setter方法.
     * @param name 属性名
     * @param cls setter方法的所属类
     * @return 指定属性名的setter方法
     */
    public static Method getSetter(String name, Class cls) {
        // 获取Setter方法名
        if (Character.isUpperCase(name.charAt(0))) {
            name =  "set" + name;
        } else {
            name = new StringBuilder("set").append(Character.toUpperCase(name.charAt(0)))
                    .append(name.substring(1)).toString();
        }
        Method[] methods = cls.getMethods();
        for(Method method : methods) {
            System.out.println(method.getName());
            if (name.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }

    public static Object[] getParamObj(Method method, List<Parameter> noDefaultParam) {
        Parameter[] parameters = method.getParameters();
        Object[] objects = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class cls = parameters[i].getType();
            Object object = null;
            try{
                object = cls.newInstance();
                objects[i] = object;
            } catch (Exception e) {
                objects[i] = parameters[i];
            }
        }
        return objects;
    }

}
