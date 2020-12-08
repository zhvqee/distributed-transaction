package com.distributed.transaction.core.transport;

import com.distributed.transaction.core.constants.DistributedTransactionConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;

public abstract class NettyServer implements RemotingServer {

    private ServerBootstrap bootstrap;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private String hostname;

    private int port;

    private int listenPort;


    public NettyServer(String hostname, int port, int listenPort) {
        this.hostname = hostname;
        this.port = port;
        this.listenPort = listenPort;
        this.bootstrap = new ServerBootstrap();
        this.bossGroup = new NioEventLoopGroup(1);
        this.workGroup = new NioEventLoopGroup(100);
        this.init();
    }


    private void init() {
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(NioChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(NioChannelOption.SO_RCVBUF, 6 * 1024 * 1024)
                .childOption(NioChannelOption.SO_SNDBUF, 6 * 1024 * 1024)
                .localAddress(new InetSocketAddress(hostname, port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProtocalDecoder(DistributedTransactionConstant.MAX_FRAME_LENGTH))
                                .addLast(new ProtocalEncoder())
                                .addLast(getMsgBussinessChannelHandler());
                    }
                });
    }

    @Override
    @SneakyThrows
    public RemotingServer bind() {
        ChannelFuture channelFuture = bootstrap.bind(listenPort).sync();
        channelFuture.channel().closeFuture().sync();
        return this;
    }

    /**
     * 获取消息处理句柄
     *
     * @return
     */
    public abstract ChannelHandler getMsgBussinessChannelHandler();

}
