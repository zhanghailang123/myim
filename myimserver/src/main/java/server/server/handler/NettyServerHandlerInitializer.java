package server.server.handler;

import com.zhl.codec.InvocationDecoder;
import com.zhl.codec.InvocationEncoder;
import com.zhl.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhanghailang
 * @date: 2021/4/1 0001 22:44
 */
@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    private static final Integer READ_TIMEOUT_SECONDS = 3 * 60;
    @Autowired
    private MessageDispatcher messageDispatcher;
    @Autowired
    private NettyServerHandler serverHandler;
    @Override
    protected void initChannel(Channel channel) throws Exception {
        //获取Channel对应的Pipeline
        ChannelPipeline pipeline = channel.pipeline();
        pipeline


                //空闲检测
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
//                //编码器
                .addLast(new InvocationEncoder())
                //解码器
                .addLast(new InvocationDecoder())

                //消息分发器
                .addLast(messageDispatcher)
                //服务端处理器
                .addLast(serverHandler)
        ;
    }
}