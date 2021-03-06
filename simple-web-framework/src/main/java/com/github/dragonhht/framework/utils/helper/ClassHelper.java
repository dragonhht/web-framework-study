package com.github.dragonhht.framework.utils.helper;

import com.github.dragonhht.framework.annotation.Controller;
import com.github.dragonhht.framework.annotation.Service;
import com.github.dragonhht.framework.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类的助手类.
 *
 * @author: huang
 * @Date: 2018-12-28
 */
public final class ClassHelper {

    /** 用于存放所加载的类. */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getBasePackage();
        CLASS_SET = ClassUtil.getClasses(basePackage);
    }

    /**
     * 获取包下的所有类.
     * @return
     */
    public static Set<Class<?>> getClasses() {
        return CLASS_SET;
    }

    /**
     * 获取包下含@Service注解的类.
     * @return
     */
    public static Set<Class<?>> getServiceClasses() {
        Set<Class<?>> classSet = new HashSet<>();
        CLASS_SET.forEach(it -> {
            if (it.isAnnotationPresent(Service.class)) {
                classSet.add(it);
            }
        });
        return classSet;
    }

    /**
     * 获取包下含@Controller注解的类.
     * @return
     */
    public static Set<Class<?>> getControllerClasses() {
        Set<Class<?>> classSet = new HashSet<>();
        CLASS_SET.forEach(it -> {
            if (it.isAnnotationPresent(Controller.class)) {
                classSet.add(it);
            }
        });
        return classSet;
    }

    /**
     * 获取应用包下的所有带指定的自定义注解的类(需交由容器管理的类).
     * @return
     */
    public static Set<Class<?>> getBeans() {
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getControllerClasses());
        classSet.addAll(getServiceClasses());
        return classSet;
    }

    /**
     * 获取基础包下某父类的所有子类.
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassesBySyper(Class<?> superClass) {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            // isAssignableFrom方法用于判断superClass是否为cls的父类
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classes.add(cls);
            }
        }
        return classes;
    }

    /**
     * 读取基础包下类上使用某注解的类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classes.add(cls);
            }
        }
        return classes;
    }

}
