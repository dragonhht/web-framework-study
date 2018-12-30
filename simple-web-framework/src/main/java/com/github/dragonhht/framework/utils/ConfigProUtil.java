package com.github.dragonhht.framework.utils;

import com.github.dragonhht.framework.exception.NotLoadFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * properties工具类.
 * User: huang
 * Date: 18-12-30
 */
public class ConfigProUtil {

    private Properties properties = new Properties();

    public ConfigProUtil(String propertiesPath) throws IOException {
        this.properties.load(new FileInputStream(new File(propertiesPath)));
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
        return this.properties.getProperty(key);
    }

    /**
     * 添加值
     * @param key
     */
    public void addValue(String key, String value) {
        this.properties.setProperty(key, value);
    }

    /**
     * 保存配置文件.
     * @param path 保存到的路径
     * @param description 描述
     * @throws IOException
     */
    public void store(String path, String description) throws IOException {
        this.properties.store(new FileOutputStream(new File(path)), description);
    }

}
