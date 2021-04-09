package com.zhl.dispatcher;

import io.netty.channel.Channel;

public interface MessageHandler<T extends Message> {

    /**
     * 执行处理消息
     * @param channel
     * @param message
     */
    void excute(Channel channel,T message);

    /**
     * 消息类型，即每个Message实现类上的TYPE静态字段
     * @return
     */
    String getType();
}
