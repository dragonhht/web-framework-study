package com.github.dragonhht.framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具类.
 * User: huang
 * Date: 18-12-30
 */
@Slf4j
public final class StreamUtil {

    /**
     * 获取字符串。
     * @param stream 输入流
     * @return
     */
    public static String getString(InputStream stream) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while (((line = reader.readLine())) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            log.error("从流中获取字符串失败", e);
        }
        return builder.toString();
    }

}
