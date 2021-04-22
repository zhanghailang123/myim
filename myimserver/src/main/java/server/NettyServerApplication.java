package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: Netty Server 启动
 * @author: zhanghailang
 * @date: 2021/4/1 0001 23:01
 */
@SpringBootApplication(scanBasePackages = {"com.zhl.dispatcher","server.server"})
public class NettyServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class,args);
    }
}