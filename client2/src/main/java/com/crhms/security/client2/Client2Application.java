package com.crhms.security.client2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
public class Client2Application {

    /**
     * 如果不加这个Listenner，不显示登录界面
     * 在这个类中，spring将每一个请求开始前都将请求进行了一次封装并设置了一个threadLocal，
     * 这样我们在请求处理的任何地方都可以通过这个threadLocal获取到请求对象
     * @return
     */
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(Client2Application.class, args);
    }

}
