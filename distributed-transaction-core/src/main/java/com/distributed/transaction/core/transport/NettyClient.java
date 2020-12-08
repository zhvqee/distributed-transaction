package com.distributed.transaction.core.transport;

import com.distributed.transaction.core.constants.DistributedTransactionConstant;
import com.distributed.transaction.core.models.MsgFufure;
import com.distributed.transaction.core.models.Response;
import com.distributed.transaction.core.models.RpcMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollMode;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public abstract class NettyClient implements RpcMsgChannelHandler, RemotingClient {

    private Bootstrap bootstrap;

    private EventLoopGroup workGroup;

    private String serverHostName;

    private Integer serverPort;

    private Channel channel;

    private Map<Integer, MsgFufure> msgFufureMap = new ConcurrentHashMap<>();

    public NettyClient(String serverHostName, Integer serverPort) {
        this.serverHostName = serverHostName;
        this.serverPort = serverPort;
        this.bootstrap = new Bootstrap();
        this.workGroup = new NioEventLoopGroup(100);
        init();
    }

    @SneakyThrows
    private void init() {
        this.bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_SNDBUF, 6 * 1000 * 1000)
                .option(ChannelOption.SO_RCVBUF, 6 * 1000 * 1000)
                .option(EpollChannelOption.EPOLL_MODE, EpollMode.EDGE_TRIGGERED)
                .option(EpollChannelOption.TCP_QUICKACK, true)
                .handler(
                        new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(
                                        new IdleStateHandler(2000, 2000, 2000))
                                        .addLast(new ProtocalDecoder(DistributedTransactionConstant.MAX_FRAME_LENGTH))
                                        .addLast(new ProtocalEncoder())
                                        .addLast(getMsgBussinessChannelHandler());
                            }
                        });


    }

    @Override
    public RemotingClient connect() {
        ChannelFuture channelFuture = this.bootstrap.connect(serverHostName, serverPort);
        channelFuture.syncUninterruptibly();
        this.channel = channelFuture.channel();
        return this;
    }

    @Override
    public Response request(RpcMessage rpcMessage, Long timeout, TimeUnit timeUnit) {

        MsgFufure msgFufure = new MsgFufure();
        msgFufure.setRpcMessage(rpcMessage);
        msgFufureMap.put(rpcMessage.getRequestId(), msgFufure);
        getChannel().writeAndFlush(rpcMessage).addListener(future -> {
            if (!future.isSuccess()) {
                msgFufure.setResult(future.cause());
            } else {
                msgFufure.setResult(true);
            }
        });

        Object result = msgFufure.get(timeout, timeUnit);
        if (result != null) {
            return Response.wrapSuccess(result);
        }
        return Response.wrapSuccess();
    }


    /**
     * 获取消息处理句柄
     *
     * @return
     */
    public abstract ChannelHandler getMsgBussinessChannelHandler();

    public Channel getChannel() {
        return channel;
    }
}
