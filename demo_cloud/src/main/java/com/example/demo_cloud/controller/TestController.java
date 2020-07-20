package com.example.demo_cloud.controller;

import com.example.demo_cloud.config.ValueConfig;
import com.example.demo_cloud.dao.entity.CdpInsightTopicalChartType;
import com.example.demo_cloud.dao.entity.PageTemplet;
import com.example.demo_cloud.dao.one.PageTempletMapper;
import com.example.demo_cloud.dao.two.CdpInsightTopicalChartTypeMapper;
import com.example.demo_cloud.dto.res.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
	
	@Autowired
	private PageTempletMapper pageTempletMapper;
	
	@Autowired
	private CdpInsightTopicalChartTypeMapper chartTypeMapper;

	@Autowired
	private ValueConfig valueConfig;

	@PostMapping("/config")
	public Object testConfig(){
		log.info("jdbc:{}",valueConfig.getJdbcUrl());
		return new HashMap<>();
	}

	@PostMapping("/db/one")
	public Object testDbOne(){
		PageTemplet tmp = pageTempletMapper.selectByPrimaryKey(new BigDecimal("41"));
		return new JsonResult<>(tmp);
	}


	@PostMapping("/db/two")
	public Object testDbTwo(){
		CdpInsightTopicalChartType cht = chartTypeMapper.selectByPrimaryKey("dc1");
		return new JsonResult(cht);
	}
	
}
