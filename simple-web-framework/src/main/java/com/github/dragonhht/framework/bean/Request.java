package com.github.dragonhht.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 请求信息.
 * User: huang
 * Date: 18-12-30
 */
@Getter
@Setter
@AllArgsConstructor
public class Request {

    /** 请求方法。 */
    private String requestMethod;
    /** 请求路径. */
    private String requestPath;

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
