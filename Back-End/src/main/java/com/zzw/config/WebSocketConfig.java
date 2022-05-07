package com.zzw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    //注入了ServerEndPointExporter 这个bean ，这个bean，会做一件事就是，把使用了@ServerEndPoint注解的bean自动注入到ioc容器中去
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
