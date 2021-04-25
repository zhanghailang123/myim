package com.zhl.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * @description: 解码器
 * @author: zhanghailang
 * @date: 2021-4-9 9:44
 */
@Slf4j
public class InvocationDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //标记当前读取的位置
        in.markReaderIndex();
        //判断是否能读取length的长度
        if (in.readableBytes() <= 4){
            return;
        }
        int length = in.readInt();
        if (length < 0){
            throw new CorruptedFrameException("negative length :" + length);
        }
        //如果message 不够可读，则退回到原读取的位置
        if (in.readableBytes() < length){
            in.resetReaderIndex();
            return;
        }
        //读取内容
        byte[] content = new byte[length];
        in.readBytes(content);
        //解析成 invocation
        Invocation invocation = JSON.parseObject(content,Invocation.class, Feature.IgnoreNotMatch);
        out.add(invocation);
        log.info("连接（{}）解析到一条消息（{}）",ctx.channel().id(),invocation.toString());
    }
}