package com.distributed.transaction.core.models;

import lombok.Data;

@Data
public class RpcMessage {

    private byte[] magicCode;

    private int version;

    private int fullLength;

    private int headerLength;

    private int msgType;

    private Integer requestId;

    private Header header;

    private Object body;
}
