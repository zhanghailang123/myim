package com.zhl.messagehandler.heartbeat;

import com.zhl.dispatcher.MessageHandler;
import com.zhl.message.heartbeat.HeartBeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: 心跳请求返回处理器2
 * @author: zhanghailang
 * @date: 2021-4-15 9:56
 */
@Component
@Slf4j
public class HeartbeatResponseHandler implements MessageHandler<HeartBeatResponse> {
    @Override
    public void excute(Channel channel, HeartBeatResponse message) {
        log.info("收到连接{}的心跳响应",channel.id());
    }

    @Override
    public String getType() {
        return HeartBeatResponse.TYPE;
    }
}