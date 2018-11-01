package cn.brightasdream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 配置websocket
 */
@Configuration
public class WebsocketConfig {

    //打成war包部署在tomcat中的话不需要这个bean
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}