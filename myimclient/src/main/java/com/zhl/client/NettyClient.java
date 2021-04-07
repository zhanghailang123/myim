package com.zhl.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description: Netty Client
 * @author: zhanghailang
 * @date: 2021-4-2 11:17
 */
@Component
@Slf4j
public class NettyClient {

    @Value("${netty.server.host}")
    private String serverHost;
    @Value("${netty.server.port}")
    private Integer serverPort;


    /**
     * 线程组，用于客户端对服务端的连接、数据读写
     */
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    /**
     * Netty Client Channel
     */
    private volatile Channel channel;

    /**
     * 启动Netty Server
     */
    @PostConstruct
    public void start(){
        //创建bootstrap对象，用于NettyClient的启动
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(serverHost,serverPort)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true);
//                .handler()
        //连接服务器，并异步等待成功，即启动客户端
        bootstrap.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                //连接失败
                if (!channelFuture.isSuccess()){
                    log.error("NettClient连接失败 {}:{}",serverHost,serverPort);
                    reconnect();
                    return;
                }
                //连接成功
                channel = channelFuture.channel();
                log.info("Netty Client连接成功 {}:{}",serverHost,serverPort);
            }
        });
    }

    public void reconnect(){

    }

    /**
     * 关闭Netty Server
     */
    public void shutdown(){
        if (channel != null){
            channel.close();
        }
        eventLoopGroup.shutdownGracefully();
    }
}