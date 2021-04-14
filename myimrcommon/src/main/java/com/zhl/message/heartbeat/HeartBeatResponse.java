package com.zhl.message.heartbeat;

import com.zhl.dispatcher.Message;

/**
 * @description: 心跳返回
 * @author: zhanghailang
 * @date: 2021/4/14 0014 23:12
 */
public class HeartBeatResponse implements Message {

    private static final String TYPE = "HEARTBEAT_RESPONSE";
    @Override
    public String toString() {
        return "HeartbeatResponse";
    }
}