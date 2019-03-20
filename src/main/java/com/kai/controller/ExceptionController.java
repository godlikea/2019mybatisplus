package com.kai.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Object authz(Exception ex) {
		if(ex instanceof UnauthorizedException) {
			return "当前用户无此权限";
		}
		return null;
	}
}
