package com.distributed.transaction.core.coordinator;

import com.distributed.transaction.core.constants.MsgType;
import com.distributed.transaction.core.models.RpcMessage;
import com.distributed.transaction.core.transactionmanager.handlers.MsgTypeProcessor;
import com.distributed.transaction.core.transactionmanager.handlers.TransactionCommitMsgTypeProcessor;
import com.distributed.transaction.core.transport.NettyServer;
import com.distributed.transaction.core.transport.RpcMsgInBoundHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class Coordinator extends NettyServer implements MsgTypeProcessor {

    private Map<Integer, MsgTypeProcessor> msgTypeProcessorMap = new HashMap<>();

    public Coordinator() {
        super("127.0.0.1", 8000, 8888);
        msgTypeProcessorMap.put(MsgType.T_COMMIT.ordinal(), new TransactionCommitMsgTypeProcessor());

    }

    @Override
    public ChannelHandler getMsgBussinessChannelHandler() {
        return new RpcMsgInBoundHandler(this);
    }

    @Override
    public void process(ChannelHandlerContext cx, RpcMessage rpcMessage) {
        msgTypeProcessorMap.get(rpcMessage.getMsgType()).process(cx, rpcMessage);
    }
}
