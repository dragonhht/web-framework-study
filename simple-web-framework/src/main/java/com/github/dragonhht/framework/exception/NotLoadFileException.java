package com.github.dragonhht.framework.exception;

/**
 * 未加载文件异常.
 *
 * @author: huang
 * @Date: 2018-12-25
 */
public class NotLoadFileException extends RuntimeException {

    public NotLoadFileException() {
    }

    public NotLoadFileException(String message) {
        super(message);
    }

    public NotLoadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotLoadFileException(Throwable cause) {
        super(cause);
    }

    public NotLoadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
