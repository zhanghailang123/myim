package server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.server.handler.NettyServerHandlerInitializer;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * @description: 消息服务端
 * @author: zhanghailang
 * @date: 2021-3-30 20:10
 */
@Component
@Slf4j
public class NettyServer {
    @Value("${netty.port}")
    private Integer port;

    @Autowired
    private NettyServerHandlerInitializer nettyServerHandlerInitializer;

    /**
     * Boss线程组，用于服务端接收客户端连接
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * worker线程组，用于服务端接受客户端的读写
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     * Netty SerVer Channel
     */
    private Channel channel;

    /**
     * 启动Netty SerVer
     */
    @PostConstruct
    public void start () throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(workerGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                //设置netty的端口
                .localAddress(new InetSocketAddress(port))
                //服务端accept的大小
                .option(ChannelOption.SO_BACKLOG,1024)
                //TCP KeepAlive机制，实现TCP层级的心跳保活功能
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                //允许较小的数据包的发送，降低延迟
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(nettyServerHandlerInitializer);

        //绑定端口，并同步等待成功，即启动服务端
        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()){
            channel = future.channel();
            log.info("Netty服务启动在端口：{}",port);
        }

    }


    public void shutdown(){
        //关闭Netty Server
        if (channel != null){
            channel.close();
        }
        //关闭两个EventLoppGroup对象
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}