package com.github.dragonhht.framework.bean;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * 属性表.
 *
 * @author: huang
 * @Date: 2019-2-21
 */
@Getter
@Setter
public class ParameterTable {

    /** 参数总数. */
    private int size;
    /** 参数索引. */
    private int index;
    /** 属性对所属的对象实例 */
    private Object target;
    /** 属性. */
    private Field field;
    /** 构造器. */
    private Constructor constructor;
    /** 是否使用构造器. */
    private boolean useConstructor;
}
