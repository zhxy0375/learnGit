package com.example.demo_cloud.dto.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
public class TableColumn {

	String name;
	String type;
	/**
	 * 格式 化后的名称
	 */
	String convertName;
	/**
	 * 格式 化后的名称
	 */
	String javaType;

	String length;

	String decimalDigits;

	boolean notNull;

	boolean primary;

	String comment;

	/**
	 * get 方法名
	 */
	String getterName;

}