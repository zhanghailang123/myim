package server.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * 1.客户端Channel的管理
 * 2.向客户端channel发送消息
 * @author: zhanghailang
 * @date: 2021-4-6 16:55
 */
@Component
@Slf4j
public class NettyChannelManager {
    /**
     * 表示Channel中对应的用户
     */
    private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");
    /**
     * channel映射
     */
    private ConcurrentHashMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();
    /**
     * 用户与channel的映射
     * 通过它，可以获取用户对应的channel。这样，我们可以向指定的用户发送消息
     */
    private ConcurrentHashMap<String,Channel> userChannels = new ConcurrentHashMap<>();

    /**
     * 添加Channel到 {@link #channels} 中
     * @param channel
     */
    public void addChannel(Channel channel){
        channels.put(channel.id(),channel);
        log.info("一个连接==>{} 加入",channel.id());
    }

    /**
     * 将用户添加到{@link #userChannels}中
     * @param channel
     * @param user
     */
    public void addUser(Channel channel,String user){
        Channel existChannel = channels.get(channel.id());
        if (existChannel == null){
            log.error("连接{} 不存在",channel.id());
            return;
        }
        //设置属性
        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        //添加到userchannels
        userChannels.put(user,channel);
    }

    /**
     * 将channel从channels和userchannels中移除
     * @param channel
     */
    public void remove(Channel channel){
        channels.remove(channel.id());
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)){
            userChannels.remove(CHANNEL_ATTR_KEY_USER);
        }
        log.info("一个连接{}离开",channel.id());
    }
}