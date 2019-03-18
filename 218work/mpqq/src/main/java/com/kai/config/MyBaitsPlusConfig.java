package com.kai.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration
@MapperScan("com.kai.mapper.*")
public class MyBaitsPlusConfig {
	
	@Bean
	public PaginationInterceptor getPaginationInterceptor() {
		PaginationInterceptor pi=new PaginationInterceptor();
		return pi;
	}
}
