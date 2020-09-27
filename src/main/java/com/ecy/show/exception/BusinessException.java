package com.ecy.show.exception;

/**
 * 业务异常类
 */
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
}