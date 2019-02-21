package com.github.dragonhht.framework.utils;

import com.github.dragonhht.framework.bean.ParameterTable;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 获取方法参数名及其的实例，若没有默认构造器则返回对象的单个字符串的构造器.
     * @param method 指定的方法
     * @return 参数实例或构造器
     */
    public static Map<String, ParamObjModel> getParamObj(Method method) {
        Map<String, ParamObjModel> objectMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        int index = 0;
        int size = parameters.length;
        for (Parameter parameter : parameters) {
            ParamObjModel model = new ParamObjModel();
            model.setIndex(index);
            Class cls = parameter.getType();
            Object object;
            try{
                object = cls.newInstance();
                model.setTarget(object);
                model.setSize(size);
                objectMap.put(parameter.getName(), model);
            } catch (Exception e) {
                try {
                    object = cls.getConstructor(String.class);
                    model.setTarget(object);
                    model.setSize(size);
                    objectMap.put(parameter.getName(), model);
                } catch (NoSuchMethodException e1) {
                    throw new RuntimeException("无法填充参数: " + parameter.getName());
                }
            }
        }
        return objectMap;
    }

    /**
     * 用于放置参数实例.
     */
    @Setter
    @Getter
    public static class ParamObjModel {
        private int index;
        private Object target;
        private int size;
    }

    /**
     * 获取填充好属性的参数实例.
     * @param paramTarget 参数属性表
     * @param params 参数
     * @return 填充好的参数实例
     */
    public static Object[] setParamValue(Map<String, ParamObjModel> paramTarget, Map<String, Object> params) {
        Map<String, ParameterTable> parameterTableMap = getParameterTable(paramTarget);
        int size = 0;
        Object[] targets = null;
        if (params != null) {
            for (String key : params.keySet()) {
                if (parameterTableMap.containsKey(key)) {
                    setParamValue(parameterTableMap.get(key), params.get(key));
                }
            }
        }
        for (Map.Entry<String, ParameterTable> entry : parameterTableMap.entrySet()) {
            ParameterTable table = entry.getValue();
            size = table.getSize();
            targets = new Object[size];
            int index = table.getIndex();
            targets[index] = table.getTarget();
        }
        return targets;
    }

    private static void setParamValue(ParameterTable table, Object value) {
        if (table.isUseConstructor()) {
            try {
                Object target = table.getConstructor().newInstance(value);
                table.setTarget(target);
            } catch (Exception e) {
                throw new RuntimeException("填充参数失败");
            }
        } else {
            ReflectionUtil.setField(table.getTarget(), table.getField(), value);
        }
    }

    /**
     * 获取参数表.
     * @param targetsMap 参数
     * @return 参数表
     */
    public static Map<String, ParameterTable> getParameterTable(Map<String, ParamObjModel> targetsMap) {
        Map<String, ParameterTable> parameterTableMap = new HashMap<>();
        for (Map.Entry<String, ParamObjModel> entry : targetsMap.entrySet()) {
            ParamObjModel model = entry.getValue();
            Object target = model.getTarget();
            if (target instanceof Constructor) {
                ParameterTable table = new ParameterTable();
                table.setConstructor((Constructor) target);
                table.setUseConstructor(true);
                table.setIndex(model.getIndex());
                table.setSize(model.getSize());
                parameterTableMap.put(entry.getKey(), table);
            } else {
                Field[] fields = target.getClass().getFields();
                for (Field field : fields) {
                    ParameterTable table = new ParameterTable();
                    table.setTarget(target);
                    table.setField(field);
                    table.setUseConstructor(false);
                    table.setIndex(model.index);
                    table.setSize(model.getSize());
                    parameterTableMap.put(field.getName(), table);
                }
            }
        }
        return parameterTableMap;
    }

}
