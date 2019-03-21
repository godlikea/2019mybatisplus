package com.kai.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * webFlux test
 * @author 郭广凯
 * @data 2019年3月21日上午11:06:29
 */
@Slf4j
@Controller
public class WebFluxController {
	
	@GetMapping(value="/reactive")
	public Flux<List<String>> getAll(){
		return null;
	}
}
