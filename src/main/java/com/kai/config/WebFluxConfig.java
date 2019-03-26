package com.kai.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebHandler;

/**
 * webFlux配置
 * @author ggk
 * @data 2019年3月25日上午10:49:20
 */
@Configuration
@ComponentScan
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
	
	@Bean
	public WebHandler getWebHandler(ApplicationContext applicationContext) {
		DispatcherHandler dispatcherHandler=new DispatcherHandler(applicationContext);
		return dispatcherHandler;
	}
}
