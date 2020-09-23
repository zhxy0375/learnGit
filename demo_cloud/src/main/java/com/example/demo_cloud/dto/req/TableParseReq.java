package com.example.demo_cloud.dto.req;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.demo_cloud.util.Tool;
import com.google.gson.JsonObject;
import lombok.Data;
import net.sf.json.JSONObject;

import java.util.List;

@Data
public class TableParseReq extends DbConfig {
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

	/**
	 * 是否需要 分页
	 */
	boolean needPage = true;
	/**
	 * 根目录  windows linux 都可以用这个 / 来分隔路径
	 */
	String baseDir = "E:/pojo/";

	public String getBaseDir() {
		if(StrUtil.isNotBlank(baseDir)) {
			baseDir = baseDir.replace("\\", Tool.FileSplit);//兼容windows路径
			//保证是目录 形式
			if(!baseDir.endsWith(Tool.FileSplit)){
				baseDir = baseDir + Tool.FileSplit;
			}
		}
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public static void main(String[] args) {
		TableParseReq req = new TableParseReq();
		System.out.println(JSONUtil.toJsonStr(req));



	}
}
