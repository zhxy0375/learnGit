package com.example.demo_cloud.dto.vo;

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
	String basePackage = "com.bl.dj";
	String author = "zxy09";
	/**
	 * controller response
	 */
	String responseClass = "JsonResult";

}

