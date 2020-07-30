package com.example.demo_cloud.service;

import com.example.demo_cloud.dto.vo.TableColumn;
import com.example.demo_cloud.dto.vo.TableIndex;
import com.example.demo_cloud.dto.vo.TableInfo;
import com.example.demo_cloud.util.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MySqlDbService {

	@Resource(name = "secondDataSource")
	DataSource dataSource;

	public List<TableInfo> parseDb(){
		List<TableInfo> tables = new ArrayList<>();

		List<TableIndex> indices = new ArrayList<>();
		try {

			Connection connection = dataSource.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();//see: https://www.cnblogs.com/dnn179/p/DatabaseMetaData.html

			ResultSet tableRs = metaData.getTables(connection.getCatalog(), connection.getCatalog(), "%", new String[]{"TABLE"});
			while (tableRs.next()) {
				String tableName = tableRs.getString("TABLE_NAME");

				if(!tableName.equals("cdp_insight_home_page_overview")){
					continue;
				}
				List<TableColumn> columns = new ArrayList<>();
				// 注释信息(COMMENT)时,发现返回的REMARKS字段返回居然是null.在数据连接url中添加了两个参数characterEncoding=utf8和useInformationSchema=true
				TableInfo table = new TableInfo(tableName, Tool.lineToHump(tableName), tableRs.getString("REMARKS"), columns);
				String primaryColumn = null;
				ResultSet idxRs = metaData.getIndexInfo(connection.getCatalog(), connection.getCatalog(), tableName, false, false);
				while (idxRs.next()) {
					String indexName = idxRs.getString("INDEX_NAME");
					if ("PRIMARY".equals(indexName)) {
						primaryColumn = idxRs.getString("COLUMN_NAME");
						continue;
					}
					List<TableIndex > exist = indices.stream().filter(o -> o.getTableName().equals(tableName) && o.getIndexName().equals(indexName)).collect(Collectors.toList());
					TableIndex index;
					if (exist != null && exist.size() > 0) {
						index = exist.get(0);
						index.setParams(index.getParams() + "," + idxRs.getString("COLUMN_NAME"));
					} else {
						index = new TableIndex();
						index.setTableName(tableName);
						index.setIndexName(indexName);
						index.setUnique(!idxRs.getBoolean("NON_UNIQUE"));
						index.setParams(idxRs.getString("COLUMN_NAME"));
						indices.add(index);
					}
				}
				ResultSet columnRs = metaData.getColumns(connection.getCatalog(), connection.getCatalog(), tableName, "%");
				while (columnRs.next()) {
					TableColumn column = new TableColumn();
					column.setName(columnRs.getString("COLUMN_NAME"));
					column.setConvertName(Tool.lineToHump(column.getName()));

					column.setType(columnRs.getString("TYPE_NAME"));
					column.setConvertType(switchToJavaType(column.getType()));

					column.setLength(columnRs.getString("COLUMN_SIZE"));
					column.setDecimalDigits(columnRs.getString("DECIMAL_DIGITS"));
					column.setNotNull(!columnRs.getBoolean("NULLABLE"));
					column.setPrimary(column.getName().equals(primaryColumn));
					column.setComment(columnRs.getString("REMARKS"));
					columns.add(column);
				}
				tables.add(table);
			}
		}
		catch (SQLException throwables) {
			log.error("",throwables);
		}
		return tables;
	}

	//把数据库类型转换成java类型
	public static String switchToJavaType(String cloumnType) {
		String fieldType = null;

		if (
				cloumnType.equals("VARCHAR")
				|| cloumnType.equals("CHAR")
				|| cloumnType.equals("TEXT")
				|| cloumnType.equals("VARCHAR")
		) {
			fieldType = "String";
		} else if (cloumnType.equals("BIGINT")) {
			fieldType = "Long";
		} else if (
				cloumnType.equals("INT")
				|| cloumnType.equals("TINYINT")
				|| cloumnType.equals("SMALLINT")
				|| cloumnType.equals("MEDIUMINT")
		) {
			fieldType = "Integer";
		}  else if (cloumnType.equals("BIT")) {
			fieldType = "Boolean";
		}else if (
				cloumnType.equals("FLOAT")
						|| cloumnType.equals("DOUBLE")
						|| cloumnType.equals("DECIMAL")
		) {
			fieldType = "BigDecimal";
		}else if (
				 cloumnType.equals("DATETIME")
						|| cloumnType.equals("DATE")
						|| cloumnType.equals("TIMESTAMP")
		) {
			fieldType = "Date";
		} else {
			fieldType = "String";
		}
		return fieldType;
	}




}
