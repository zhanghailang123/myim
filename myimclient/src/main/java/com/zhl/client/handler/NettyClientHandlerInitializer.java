package com.zhl.client.handler;

import com.zhl.codec.InvocationDecoder;
import com.zhl.codec.InvocationEncoder;
import com.zhl.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhanghailang
 * @date: 2021-4-6 16:43
 */
@Component
public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {
    /**
     * 心跳超时时间
     */
    private static final Integer READ_TIMEOUT_SECONDS = 60;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private NettyClientHandler nettyClientHandler;
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new IdleStateHandler(READ_TIMEOUT_SECONDS, 0, 0))
                .addLast(new ReadTimeoutHandler(3 * READ_TIMEOUT_SECONDS))
                .addLast(new InvocationDecoder())
                .addLast(new InvocationEncoder())
                .addLast(messageDispatcher)
                .addLast(nettyClientHandler);
    }
}