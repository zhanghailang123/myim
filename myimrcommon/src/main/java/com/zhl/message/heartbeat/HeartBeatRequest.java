package com.zhl.message.heartbeat;

import com.zhl.dispatcher.Message;

/**
 * @description: 心跳请求
 * @author: zhanghailang
 * @date: 2021/4/14 0014 22:59
 */
public class HeartBeatRequest implements Message {
    public static final String TYPE = "HEARTBEAT_REQUEST";

    @Override
    public String toString() {
        return "HeartbeatRequest";
    }
}