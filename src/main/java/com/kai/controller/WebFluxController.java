package com.kai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * webFlux test
 * @author 郭广凯
 * @data 2019年3月21日上午11:06:29
 */
@Slf4j
@Controller
@RequestMapping("/flux")
public class WebFluxController {
	
	@GetMapping(value="/reactive")
	public Flux<String> getAll(){
		log.info("==============");
		Flux<String> flux=Flux.just("a","b","c");
		return flux;
	}
}
