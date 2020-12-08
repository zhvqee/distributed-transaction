package com.distributed.transaction.core.transactionmanager.handlers;

import com.distributed.transaction.core.models.RpcMessage;
import io.netty.channel.ChannelHandlerContext;

public class TransactionCommitMsgTypeProcessor implements MsgTypeProcessor {
    @Override
    public void process(ChannelHandlerContext cx, RpcMessage rpcMessage) {
        System.out.println("remote client:" + cx.channel().remoteAddress().toString() + "channel:" + cx.channel() + ",rpcMessage:" + rpcMessage);
    }
}
