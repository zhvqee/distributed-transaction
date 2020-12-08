package com.distributed.transaction.core.models;

import lombok.Data;

@Data
public class Response {

    private Integer requestId;

    private boolean success;

    private Object data;

    private String msg;

    public static Response wrapSuccess() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static Response wrapSuccess(Object object) {
        Response response = new Response();
        response.setSuccess(true);
        response.setData(object);
        return response;
    }


    public static Response wrapError(String msg) {
        Response response = new Response();
        response.setSuccess(false);
        response.setMsg(msg);
        return response;
    }
}
