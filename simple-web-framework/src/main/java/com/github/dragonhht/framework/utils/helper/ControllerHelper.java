package com.github.dragonhht.framework.utils.helper;

import com.github.dragonhht.framework.annotation.RequestMapping;
import com.github.dragonhht.framework.bean.Handler;
import com.github.dragonhht.framework.bean.Request;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Controller助手类.
 * User: huang
 * Date: 18-12-30
 */
@Slf4j
public final class ControllerHelper {

    /** 请求与处理器的映射关系。*/
    private static final Map<Request, Handler> CONTROLLER_MAP = new HashMap<>();

    static {
        // 获取所有的Controller类
        Set<Class<?>> controllerClasses = ClassHelper.getControllerClasses();
        if (controllerClasses != null) {
            for (Class<?> controllerClass : controllerClasses) {
                Method[] methods = controllerClass.getDeclaredMethods();
                if (methods != null) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            // 获取路由信息
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            String uri = requestMapping.value();
                            String requestMethod = requestMapping.method();
                            // 验证URL映射规则
                            if (StringUtils.isNotEmpty(uri)) {
                                Request request = new Request(requestMethod, uri);
                                Handler handler = new Handler(controllerClass, method);
                                // 添加请求与处理器的映射
                                CONTROLLER_MAP.put(request, handler);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 获取处理器.
     * @param requestMethod 请求方法
     * @param requestUri 请求uri
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestUri) {
        Request request = new Request(requestMethod, requestUri);
        return CONTROLLER_MAP.get(request);
    }

}
