package com.zhl.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 编码器
 * MessageToByteEncoder的使用
 * @author: zhanghailang
 * @date: 2021/4/8 0008 22:54
 */
@Slf4j
public class InvocationEncoder extends MessageToByteEncoder<Invocation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Invocation msg, ByteBuf out) throws Exception {
        //将Invocation转化为Byte数组
        byte[] content = JSON.toJSONBytes(msg);
        //写入length
        out.writeInt(content.length);
        //写入内容
        out.writeBytes(content);
        log.info("连接（{}）编码了一条（{}）消息",ctx.channel().id(),msg.toString());
    }
}