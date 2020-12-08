package com.distributed.transaction.core.transport;

import com.distributed.transaction.core.models.RpcMessage;
import com.distributed.transaction.core.transactionmanager.handlers.MsgTypeProcessor;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

@ChannelHandler.Sharable
public class RpcMsgInBoundHandler extends ChannelDuplexHandler {

    private MsgTypeProcessor msgTypeProcessor;

    public RpcMsgInBoundHandler(MsgTypeProcessor msgTypeProcessor) {
        this.msgTypeProcessor = msgTypeProcessor;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RpcMessage) {
            RpcMessage rpcMessage = (RpcMessage) msg;
            msgTypeProcessor.process(ctx, rpcMessage);
            ctx.fireChannelRead(msg);
        }
    }


}
