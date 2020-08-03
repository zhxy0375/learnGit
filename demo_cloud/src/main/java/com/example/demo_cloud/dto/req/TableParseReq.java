package com.example.demo_cloud.dto.req;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.List;

@Data
public class TableParseReq {
	/**
	 * 要解析的表名列表
	 */
	List<String> tableNames;

	/**
	 * 基础包路径
	 */
	String basePackage = "";
	/**
	 * 类文件 作者
	 */
	String author = "zxy09";

	/**
	 * Controller response类 全路径
	 */
	String responseClassPath = "";

	/**
	 * Controller response类
	 */
	String responseClass = "";  //""JsonResult<T>";

	String mapperLocation;
	String entityLocation;
	String serviceLocation;
	String controllerLocation;

}
