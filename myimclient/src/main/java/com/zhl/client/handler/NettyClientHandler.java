package com.zhl.client.handler;

import com.zhl.client.NettyClient;
import com.zhl.codec.Invocation;
import com.zhl.message.heartbeat.HeartBeatRequest;
import io.netty.channel.ChannelFutureListener;
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
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//
        //发起重连
        nettyClient.reconnect();
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("连接{} 发生异常",ctx.channel().id(),cause);
//        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent){
            HeartBeatRequest heartBeatRequest = new HeartBeatRequest();
            ctx.writeAndFlush(new Invocation(HeartBeatRequest.TYPE,heartBeatRequest))
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            log.info("发起一次心跳");
        }
    }
}