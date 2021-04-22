package com.zhl.client.handler;

import com.zhl.dispatcher.MessageHandler;
import com.zhl.message.auth.AuthResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: 认证请求返回处理器
 * @author: zhanghailang
 * @date: 2021-4-19 23:22
 */
@Component
@Slf4j
public class AuthResponseHandler implements MessageHandler<AuthResponse> {
    @Override
    public void excute(Channel channel, AuthResponse message) {
        log.info("认证结果为({})",message);
    }

    @Override
    public String getType() {
        return AuthResponse.TYPE;
    }
}