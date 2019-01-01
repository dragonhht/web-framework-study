package com.github.dragonhht.web.aop;

import com.github.dragonhht.framework.annotation.Controller;
import com.github.dragonhht.framework.aop.annotation.Aspect;
import com.github.dragonhht.framework.aop.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * 测试AOP增强@Controller修饰的类.
 *
 * @author: huang
 * @Date: 2019-1-1
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private long start;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        System.out.println("start " + cls.getName() + " " + method.getName());
        start = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) {
        System.out.println("end " + cls.getName() + " " + method.getName() + " for" + (System.currentTimeMillis() - start));
    }
}
