package com.zhl.codec;

import lombok.Data;

/**
 * @description: 通信协议的消息体
 * @author: zhanghailang
 * @date: 2021/4/8 0008 22:39
 */
@Data
public class Invocation {
    /**
     *  类型
     */
    private String type;
    /**
     * 消息，JSON格式
     */
    private String message;

    @Override
    public String toString() {
        return "Invocation{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}