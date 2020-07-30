package com.example.demo_cloud.dto.vo;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Data
public class TableIndex {

	String tableName;

	String indexName;

	boolean unique;

	List<String> columns;

	public void addParams(String column) {
		if (CollectionUtil.isEmpty(columns)) {
			columns = new ArrayList<>();
			columns.add(column);
		} else {
			columns.add(column);
		}
	}
}