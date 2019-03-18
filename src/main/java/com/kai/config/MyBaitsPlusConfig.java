package com.kai.config;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
/**
 * mybaits 分页
 * @author 郭广凯
 * @data 2019年3月18日下午4:04:11
 */
@Configuration
@MapperScan("com.kai.mapper.*")
public class MyBaitsPlusConfig {
	/**
	 * 分页插件
	 * @author 郭广凯
	 * @data 2019年3月18日下午4:52:23
	 * @return
	 */
	@Bean
	public PaginationInterceptor getPaginationInterceptor() {
		PaginationInterceptor pi=new PaginationInterceptor();
		List<ISqlParser> sqlParserList = new ArrayList<>();
	    // 攻击 SQL 阻断解析器、加入解析链
	    sqlParserList.add(new BlockAttackSqlParser());
	    pi.setSqlParserList(sqlParserList);
		return pi;
	}
	 /**
     * SQL执行效率插件
     */
    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
    /**
     * 乐观锁注入  意图：当要更新一条记录的时候，希望这条记录没有被别人更新
     * 乐观锁实现方式：

		取出记录时，获取当前version
		更新时，带上这个version
		执行更新时， set version = newVersion where version = oldVersion
		如果version不对，就更新失败
     * @author 郭广凯
     * @data 2019年3月18日下午4:52:18
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor getOptimisticLocker() {
    	return new OptimisticLockerInterceptor();
    }
}
