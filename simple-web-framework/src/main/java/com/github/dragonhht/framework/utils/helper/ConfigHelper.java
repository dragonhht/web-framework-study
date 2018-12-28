package com.github.dragonhht.framework.utils.helper;

import com.github.dragonhht.framework.utils.ConfigProUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 配置文件助手类.
 *
 * @author: huang
 * @Date: 2018-12-28
 */
@Slf4j
public class ConfigHelper {

    private static final ConfigProUtil CONFIG;

    static {
        ConfigProUtil CONFIG_NEW;
        try {
            CONFIG_NEW = new ConfigProUtil("simple.properties");
        } catch (IOException e) {
            CONFIG_NEW = null;
            log.error("获取配置文件失败", e);
        }
        CONFIG = CONFIG_NEW;
    }

    /**
     * 获取基础包.
     * @return
     */
    public static String getBasePackage() {
        return CONFIG.getValue("simple.web.framework.base_package");
    }

    /**
     * 获取视图基础路径.
     * @return
     */
    public static String getViewPath() {
        return CONFIG.getValue("simple.web.framework.jsp_path");
    }

}
