package com.github.dragonhht.framework.utils;

import com.github.dragonhht.framework.exception.NotLoadFileException;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * 读取properties文件的类.
 */
public final class ConfigProReaderUtil {

    /** 配置文件. */
    private ResourceBundle properties = null;

    public ConfigProReaderUtil(String propertiesName) throws IOException {
        properties = ResourceBundle.getBundle(propertiesName);
    }

    /**
     * 通过键获取值.
     * @param key 键
     * @return
     */
    public String getValue(String key) {
        if (this.properties == null) {
            throw new NotLoadFileException("文件未正确加载!");
        }
        return this.properties.getString(key);
    }
}