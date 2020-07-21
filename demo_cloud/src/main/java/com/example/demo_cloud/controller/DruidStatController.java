package com.example.demo_cloud.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 #druid sql监控
 http://localhost:8181/dc/druid/index.html

 http://localhost:8181/dc/druid/sql.json?orderBy=SQL&orderType=desc&page=1&perPageCount=1000000

 */
@RestController
public class DruidStatController {

//	@GetMapping("/druid/stat")  使用这个 会跳到 druid监控 界面
	@GetMapping("/db/stat")
	public Object druidStat(){
		// DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
		return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
	}

}
