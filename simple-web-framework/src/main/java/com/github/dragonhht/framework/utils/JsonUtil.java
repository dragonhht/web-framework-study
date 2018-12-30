package com.github.dragonhht.framework.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * JSON工具类.
 * User: huang
 * Date: 18-12-30
 */
@Slf4j
public final class JsonUtil {

    private static final ObjectMapper JSON_UTIL = new ObjectMapper();

    /**
     * 将POJO转成JSON.
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        String json = null;
        try {
            json = JSON_UTIL.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("转换JSON失败", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将JSON转为POJO.
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        T result = null;
        try {
            result = JSON_UTIL.readValue(json, tClass);
        } catch (IOException e) {
            log.error("转换JSON失败", e);
            throw new RuntimeException(e);
        }
        return result;
    }

}
