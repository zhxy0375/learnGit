package com.example.demo_cloud.dto.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
public class TableIndex {

	String tableName;

	String indexName;

	boolean unique;

	String params;

	public void addParams(String param) {
		if (StringUtils.isBlank(params)) {
			params = param;
		} else {
			params = params + "," + param;
		}
	}
}