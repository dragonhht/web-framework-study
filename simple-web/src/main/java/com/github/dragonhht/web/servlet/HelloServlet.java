package com.github.dragonhht.web.servlet;

import com.github.dragonhht.framework.annotation.Autowired;
import com.github.dragonhht.framework.annotation.Controller;
import com.github.dragonhht.framework.annotation.RequestMapping;
import com.github.dragonhht.framework.bean.Param;
import com.github.dragonhht.framework.bean.View;
import com.github.dragonhht.web.service.HelloService;

/**
 * 描述.
 *
 * @author: huang
 * @Date: 18-12-17
 */
@Controller
public class HelloServlet {

    @Autowired
    private HelloService service;

    @RequestMapping(value = "/hello")
    public View Hello(Param param) {
        System.out.println(service.getTime());
        View view = new View("hello.jsp");
        return view;
    }
}
