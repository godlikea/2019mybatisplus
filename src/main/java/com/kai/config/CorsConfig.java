package com.kai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	/**
	 * 设置跨域
	 * @author 郭广凯
	 * @data 2019年3月20日下午5:05:55
	 * @return
	 */
	@Bean
	public CorsFilter getCorsFilter() {
		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
		CorsConfiguration cors=new CorsConfiguration();
		cors.addAllowedHeader("*");
		cors.addAllowedMethod("*");
		cors.addAllowedOrigin("*");
		source.registerCorsConfiguration("/**", cors);
		return new CorsFilter(source);
	}
}
