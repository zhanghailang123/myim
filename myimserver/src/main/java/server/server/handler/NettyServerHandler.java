package server.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.server.NettyChannelManager;

/**
 * @description:
 * @author: zhanghailang
 * @date: 2021-4-7 19:52
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private NettyChannelManager channelManager;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        //从管理器中添加
        channelManager.addChannel(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        //从管理器中移除
        channelManager.remove(ctx.channel());
//        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("连接{}发生异常",ctx.channel().id(),cause);
//        super.exceptionCaught(ctx, cause);
        //断开连接
        ctx.channel().close();
    }

}