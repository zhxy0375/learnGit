package com.example.demo_cloud.dto.vo;

import cn.hutool.core.util.StrUtil;
import com.example.demo_cloud.util.Tool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TableInfo {

	public TableInfo() {
	}

	public TableInfo(String tableName, String convertName, String tableComment, List<TableColumn> columnList) {
		this.tableName = tableName;
		this.convertName = convertName;
		this.tableComment = tableComment;
		this.columnList = columnList;

		this.className = Tool.upperFirstChar(convertName);
		this.mappingPath = Tool.mappingPath(tableName);
	}

	String tableName;
	/**
	 * 格式 化后的名称
	 */
	String convertName;

	String tableComment;

	List<TableColumn> columnList = new ArrayList<>();

	String className;

	String mappingPath;

	List<TableColumn> primaryColumns;

	List<TableColumn> uniqueColumns;

	/**
	 * 实体json串
	 */
	String entityJson;

	/**
	 * 基础包路径
	 */
	String basePackage = "";//""com.bl.dj";
	String author = "zxy09";

	/**
	 * controller response 全路径
	 */
	String responseClassPath = "";//""com.example.demo_cloud.dto.res.JsonResult";
	/**
	 * controller response
	 */
	String responseClass = "";  //""JsonResult<T>";

	String mapperLocation;
	String entityLocation;
	String serviceLocation;
	String controllerLocation;

	public String getResponseClass() {
		if(StrUtil.isBlank(responseClass) && StrUtil.isNotBlank(responseClassPath)){
			int ind = responseClassPath.lastIndexOf(".");
			if(ind>0){
				responseClass = responseClassPath.substring(ind+1);
			}else {
				return responseClassPath;
			}
		}
		return responseClass;
	}

	public String getMapperLocation() {
		if(StrUtil.isBlank(mapperLocation) && StrUtil.isNotBlank(basePackage)){
			mapperLocation = basePackage + ".mapper";
		}
		return mapperLocation;
	}

	public String getEntityLocation() {
		if(StrUtil.isBlank(entityLocation) && StrUtil.isNotBlank(basePackage)){
			entityLocation = basePackage + ".entity";
		}
		return entityLocation;
	}

	public String getServiceLocation() {
		if(StrUtil.isBlank(serviceLocation) && StrUtil.isNotBlank(basePackage)){
			serviceLocation = basePackage + ".service";
		}
		return serviceLocation;
	}

	public String getControllerLocation() {
		if(StrUtil.isBlank(controllerLocation) && StrUtil.isNotBlank(basePackage)){
			controllerLocation = basePackage + ".controller";
		}
		return controllerLocation;
	}
}

