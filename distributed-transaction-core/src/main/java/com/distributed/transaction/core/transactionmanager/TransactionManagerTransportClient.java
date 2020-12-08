package com.distributed.transaction.core.transactionmanager;

import com.distributed.transaction.core.constants.MsgType;
import com.distributed.transaction.core.models.Response;
import com.distributed.transaction.core.models.RpcMessage;
import com.distributed.transaction.core.transactionmanager.handlers.MsgTypeProcessor;
import com.distributed.transaction.core.transactionmanager.handlers.TransactionCommitMsgTypeProcessor;
import com.distributed.transaction.core.transport.NettyClient;
import com.distributed.transaction.core.transport.RpcMsgInBoundHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class TransactionManagerTransportClient extends NettyClient implements MsgTypeProcessor {


    private Map<Integer, MsgTypeProcessor> msgTypeProcessorMap = new HashMap<>();

    public TransactionManagerTransportClient(String serverHostName, Integer serverPort) {
        super(serverHostName, serverPort);
        msgTypeProcessorMap.put(MsgType.T_COMMIT.ordinal(), new TransactionCommitMsgTypeProcessor());
    }

    @Override
    public ChannelHandler getMsgBussinessChannelHandler() {
        return new RpcMsgInBoundHandler(this);
    }


    @Override
    public Response response(RpcMessage rpcMessage) {

        return Response.wrapSuccess();
    }

    @Override
    public void process(ChannelHandlerContext cx, RpcMessage rpcMessage) {
        MsgTypeProcessor msgTypeProcessor = msgTypeProcessorMap.get(rpcMessage.getMsgType());
        msgTypeProcessor.process(cx, rpcMessage);
    }
}
