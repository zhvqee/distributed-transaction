package com.distributed.transaction.core.exceptions;

public class DTResolvingException extends RuntimeException {

    private static final String KEY = "数据解析异常";

    public DTResolvingException(String message) {
        super(KEY + message);
    }
}
