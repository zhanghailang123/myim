package com.zhl.dispatcher;

import com.alibaba.fastjson.JSON;
import com.zhl.codec.Invocation;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: zhanghailang
 * @date: 2021/4/12 0012 22:42
 */
@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {
    @Autowired
    private MessageHandlerContainer messageHandlerContainer;
    private final ExecutorService executorService = Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation msg) throws Exception {
        //获取Type对应的Messagehandler处理器
        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(msg.getType());
        //获取Messagehandler处理器的消息类
        Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
        //解析消息
        Message message = JSON.parseObject(msg.getMessage(),messageClass);
        //执行逻辑
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                messageHandler.excute(ctx.channel(),message);
            }
        });
    }
}