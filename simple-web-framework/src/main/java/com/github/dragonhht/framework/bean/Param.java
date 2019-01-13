package com.github.dragonhht.framework.bean;

import java.util.Map;

/**
 * 请求参数对象.
 * User: huang
 * Date: 18-12-30
 */
public class Param {

    private Map<String, Object> params;

    public Param(Map<String, Object> params) {
        this.params = params;
    }

    /** 获取long型参数。 */
    public long getLong(String name) {
        return Long.class.cast(params.get(name));
    }

    /**
     * 获取所有字段信息.
     * @return
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * 判断参数是否为空.
     * @return
     */
    public boolean isEmpty() {
        return params == null || params.size() == 0;
    }

}
