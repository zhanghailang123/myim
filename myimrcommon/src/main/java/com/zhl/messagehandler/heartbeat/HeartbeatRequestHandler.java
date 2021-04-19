package com.zhl.messagehandler.heartbeat;

import com.zhl.codec.Invocation;
import com.zhl.dispatcher.MessageHandler;
import com.zhl.message.heartbeat.HeartBeatRequest;
import com.zhl.message.heartbeat.HeartBeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: 心跳请求处理器
 * @author: zhanghailang
 * @date: 2021-4-15 9:57
 */
@Component
@Slf4j
public class HeartbeatRequestHandler implements MessageHandler<HeartBeatRequest> {
    @Override
    public void excute(Channel channel, HeartBeatRequest message) {
        log.info("收到连接{} 的心跳请求",channel.id());
        HeartBeatResponse heartBeatResponse = new HeartBeatResponse();
        channel.writeAndFlush(new Invocation(HeartBeatResponse.TYPE,heartBeatResponse));
    }

    @Override
    public String getType() {
        return HeartBeatRequest.TYPE;
    }
}