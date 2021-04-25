package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @description: Netty Server 启动
 * @author: zhanghailang
 * @date: 2021/4/1 0001 23:01
 */
@SpringBootApplication
@ComponentScan(value = {"com.zhl","server.server"})
public class NettyServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class,args);
    }
}