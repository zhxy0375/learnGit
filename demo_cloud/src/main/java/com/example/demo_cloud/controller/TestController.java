package com.example.demo_cloud.controller;

import com.example.demo_cloud.config.ValueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

	@Autowired
	private ValueConfig valueConfig;

	@PostMapping("/config")
	public Object testConfig(){
		log.info("jdbc:{}",valueConfig.getJdbcUrl());
		return new HashMap<>();
	}
}
