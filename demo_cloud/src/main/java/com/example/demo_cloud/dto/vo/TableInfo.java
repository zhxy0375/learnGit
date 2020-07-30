package com.example.demo_cloud.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {

	String tableName;
	/**
	 * 格式 化后的名称
	 */
	String convertName;

	String tableComment;

	List<TableColumn> columnList = new ArrayList<>();

}

