package com.kai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kai.mapper.UserMapper;
import com.kai.model.TUser;

import reactor.core.publisher.Flux;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
}
