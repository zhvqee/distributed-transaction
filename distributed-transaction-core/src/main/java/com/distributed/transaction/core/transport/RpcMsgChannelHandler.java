package com.distributed.transaction.core.transport;

import com.distributed.transaction.core.models.Response;
import com.distributed.transaction.core.models.RpcMessage;

import java.util.concurrent.TimeUnit;

public interface RpcMsgChannelHandler {

    /**
     * 消息请求
     *
     * @param rpcMessage
     */
    Response request(RpcMessage rpcMessage, Long timeout, TimeUnit timeUnit);

    /**
     * 消息回复
     *
     * @param rpcMessage
     */
    Response response(RpcMessage rpcMessage);

}
