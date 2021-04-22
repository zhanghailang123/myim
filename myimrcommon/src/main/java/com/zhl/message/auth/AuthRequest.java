package com.zhl.message.auth;

import com.zhl.dispatcher.Message;

/**
 * @description: 认证请求
 * @author: zhanghailang
 * @date: 2021-4-19 22:41
 */
public class AuthRequest implements Message {
    public static final String TYPE = "AUTH_REQUEST";
    private String accessToken;

    public static String getTYPE() {
        return TYPE;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}