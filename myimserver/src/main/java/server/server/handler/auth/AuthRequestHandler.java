package server.server.handler.auth;

import com.zhl.codec.Invocation;
import com.zhl.dispatcher.MessageHandler;
import com.zhl.message.auth.AuthRequest;
import com.zhl.message.auth.AuthResponse;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import server.server.NettyChannelManager;

/**
 * @description: 认证请求处理器
 * @author: zhanghailang
 * @date: 2021-4-19 22:55
 */
@Component
public class AuthRequestHandler implements MessageHandler<AuthRequest> {

    @Autowired
    private NettyChannelManager channelManager;

    @Override
    public void excute(Channel channel, AuthRequest authRequest) {
        if (StringUtils.isEmpty(authRequest.getAccessToken())){
            AuthResponse response = new AuthResponse().setCode(1).setMessage("认证AccessToken未传入");
            channel.writeAndFlush(new Invocation(AuthResponse.TYPE,response));
            return;
        }
        //TODO 此处先将代码简化 暂时现将AccessToken当做user
        channelManager.addUser(channel,authRequest.getAccessToken());


        AuthResponse authResponse = new AuthResponse().setCode(0);
        channel.writeAndFlush(new Invocation(AuthResponse.TYPE,authResponse));
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}