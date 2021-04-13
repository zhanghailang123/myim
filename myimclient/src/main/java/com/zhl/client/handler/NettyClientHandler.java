package com.zhl.client.handler;

import com.zhl.client.NettyClient;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhanghailang
 * @date: 2021/4/13 0013 22:04
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private NettyClient nettyClient;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//
        //发起重连
        nettyClient.reconnect();
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("连接{} 发生异常",ctx.channel().id());
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent){
            log.info("发起一次心跳");

        }
    }
}