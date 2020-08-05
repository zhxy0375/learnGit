package com.example.demo_cloud.controller;

import com.example.demo_cloud.config.ValueConfig;
import com.example.demo_cloud.dto.req.TableParseReq;
import com.example.demo_cloud.dto.res.JsonResult;
import com.example.demo_cloud.service.MySqlDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

	@Autowired
	private ValueConfig valueConfig;

	@Autowired
	private MySqlDbService mySqlDbService;

	@GetMapping("/config")
	public Object testConfig(){
		log.info("jdbc:{}",valueConfig.getJdbcUrl());
		return new HashMap<>();
	}


	@PostMapping("/db/parse")
	public Object parseDb(@RequestBody TableParseReq req){
		mySqlDbService.genJavaFiles(req);
		return new JsonResult("");
	}
	
}
