package com.zhl.dispatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description:
 * @author: zhanghailang
 * @date: 2021/4/11 0011 00:09
 */
@Slf4j
public class MessageHandlerContainer implements InitializingBean {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 消息类型和MessageHandler的映射
     */

    private final Map<String,MessageHandler> handlers = new HashMap<String, MessageHandler>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //通过ApplicationContext获得所有MessageHandler Bean
        applicationContext.getBeansOfType(MessageHandler.class).values()
                .forEach(messagehandler ->
                handlers.put(messagehandler.getType(),messagehandler));
    }


    /**
     * 获得类型对应的MessageHandler
     * @param type
     * @return
     */
    MessageHandler getMessageHandler(String type){
        MessageHandler handler = handlers.get(type);
        if (handler == null){
            throw new IllegalArgumentException("找不到匹配类型的MessageHandler处理器");
        }
        return handler;
    }


    /**
     *  获取MessageHandler处理的消息类
     * @param handler
     * @return
     */
    static Class<? extends Message> getMessageClass(MessageHandler handler){
        //获取bean对应的Class类名，因为有可能被AOP代理过
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);
        //获取接口的Type数组
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superClass = targetClass.getSuperclass();

        while ((Objects.isNull(interfaces) || 0 == interfaces.length) && Objects.nonNull(superClass)){
            interfaces = superClass.getGenericInterfaces();
            superClass = targetClass.getSuperclass();

        }
        if (Objects.nonNull(interfaces)){
            //遍历interfaces数组
            for (Type type : interfaces){
                //要求type是泛型参数
                if (type instanceof ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    //要求是MessageHandler接口
                    if (Objects.equals(parameterizedType.getRawType(),MessageHandler.class)){
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        //取首个元素
                        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0){
                            return (Class<Message>) actualTypeArguments[0];
                        } else {
                            throw new IllegalStateException(String.format("类型(%s)获取不到消息类型",handler));
                        }
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s)获取不到消息类型",handler));
    }

}