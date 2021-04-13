package server.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhanghailang
 * @date: 2021/4/1 0001 22:44
 */
@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {
    @Autowired
    private NettyServerHandler serverHandler;
    @Override
    protected void initChannel(Channel channel) throws Exception {
        //获取Channel对应的Pipeline
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(serverHandler);
    }
}