package com.github.dragonhht.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 视图对象.
 * User: huang
 * Date: 18-12-30
 */
@Getter
@AllArgsConstructor
public class View {

    /** 试图路径. */
    private String path;
    /** 模型数据. */
    private Map<String, Object> model;


    public View(String path) {
        this.path = path;
    }

}
