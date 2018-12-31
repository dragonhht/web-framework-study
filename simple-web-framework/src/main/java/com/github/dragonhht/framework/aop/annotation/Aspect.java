package com.github.dragonhht.framework.aop.annotation;

import java.lang.annotation.*;

/**
 * AOP注解.
 * User: huang
 * Date: 18-12-31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();

}
