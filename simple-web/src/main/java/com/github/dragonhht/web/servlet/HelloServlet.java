package com.github.dragonhht.web.servlet;

import com.github.dragonhht.web.service.HelloService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述.
 *
 * @author: huang
 * @Date: 18-12-17
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    private HelloService service = new HelloService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(service.getTime());
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
    }
}
