package com.github.dragonhht.framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编解码工具类.
 * User: huang
 * Date: 18-12-30
 */
@Slf4j
public final class CodecUtil {

    /**
     * 将字符串进行URL编码.
     * @param source 需编码的字符串
     * @return
     */
    public static String encodeURL(String source) {
        String str = null;
        try {
            str = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("对字符串进行URL编码失败", e);
        }
        return str;
    }

    /**
     * 将字符串进行URL解码.
     * @param source 需解码的字符串
     * @return
     */
    public static String decodeURL(String source) {
        String str = null;
        try {
            str = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("对字符串进行URL解码失败", e);
        }
        return str;
    }

}
