package com.distributed.transaction.core.transactionmanager.handlers;

import com.distributed.transaction.core.models.RpcMessage;
import io.netty.channel.ChannelHandlerContext;

public interface MsgTypeProcessor {

    void process(ChannelHandlerContext cx, RpcMessage rpcMessage);

}
