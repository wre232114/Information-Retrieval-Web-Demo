package cn.brightasdream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import cn.brightasdream.controller.WebsocketServer;


@SpringBootApplication
@EnableCaching
public class Application {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
		//解决WebSocket不能注入问题
		WebsocketServer.setApplicationContext((ApplicationContext)configurableApplicationContext);
	}
}
