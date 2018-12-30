package com.github.dragonhht.web.service;

import com.github.dragonhht.framework.annotation.Service;

import java.util.Date;

/**
 * 描述.
 *
 * @author: huang
 * @Date: 18-12-18
 */
@Service
public class HelloService {

    public String getTime() {
        return new Date().toString();
    }

}
