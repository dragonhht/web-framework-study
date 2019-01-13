package com.github.dragonhht.framework.handler;

import com.github.dragonhht.framework.bean.Data;
import com.github.dragonhht.framework.bean.Handler;
import com.github.dragonhht.framework.bean.Param;
import com.github.dragonhht.framework.bean.View;
import com.github.dragonhht.framework.utils.CodecUtil;
import com.github.dragonhht.framework.utils.JsonUtil;
import com.github.dragonhht.framework.utils.ReflectionUtil;
import com.github.dragonhht.framework.utils.StreamUtil;
import com.github.dragonhht.framework.utils.helper.BeanHelper;
import com.github.dragonhht.framework.utils.helper.ConfigHelper;
import com.github.dragonhht.framework.utils.helper.ControllerHelper;
import com.github.dragonhht.framework.utils.helper.Helperloader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器.
 * User: huang
 * Date: 18-12-30
 */
@Slf4j
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求方法与请求路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        // 获取处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            // 获取Controller 及其实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerInstance = BeanHelper.getBean(controllerClass);
            // 创建请求参数对象
            Map<String, Object> params = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String name = paramNames.nextElement();
                String value = req.getParameter(name);
                params.put(name, value);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] bodyParams = StringUtils.split(body, "&");
                if (bodyParams != null) {
                    for (String par : bodyParams) {
                        String[] arr = StringUtils.split(par, "=");
                        if (arr != null && arr.length == 2) {
                            String parName = arr[0];
                            String parValue = arr[1];
                            params.put(parName, parValue);
                        }
                    }
                }
            }
            Param param = new Param(params);
            Method method = handler.getActionMethod();
            Object result;
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerInstance, method);
            } else {
                result = ReflectionUtil.invokeMethod(controllerInstance, method, param);
            }

            // 处理返回值
            if (result instanceof View) {
                // 返回JSP页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        if (model != null) {
                            for (Map.Entry<String, Object> entry : model.entrySet()) {
                                req.setAttribute(entry.getKey(), entry.getValue());
                            }
                        }
                        req.getRequestDispatcher(ConfigHelper.getViewPath() + path).forward(req, resp);
                    }
                }
            } else if (result instanceof Data) {
                // 返回JSON数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter out = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    out.write(json);
                    out.flush();
                    out.close();
                }
            }
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化Helper类
        Helperloader.init();
        // 获取ServletContext,用于注册Servlet
        ServletContext servletContext = config.getServletContext();
        // 注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getViewPath() + "*");
        // 注册处理静态资源的默认Servlet
        //ServletRegistration defaultServlet = servletContext.getServletRegistration("default");

    }
}
