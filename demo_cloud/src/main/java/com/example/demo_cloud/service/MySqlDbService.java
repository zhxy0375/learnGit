package com.example.demo_cloud.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo_cloud.dto.vo.TableColumn;
import com.example.demo_cloud.dto.vo.TableIndex;
import com.example.demo_cloud.dto.vo.TableInfo;
import com.example.demo_cloud.util.FreeMarkerUtil;
import com.example.demo_cloud.util.Tool;
import com.google.common.collect.Tables;
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

	public List<TableInfo> parseDb(List<String> tableNameLst){
		List<TableInfo> tables = new ArrayList<>();

		List<TableIndex> unqueIndices = new ArrayList<>();
		try {

			Connection connection = dataSource.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();//see: https://www.cnblogs.com/dnn179/p/DatabaseMetaData.html

			ResultSet tableRs = metaData.getTables(connection.getCatalog(), connection.getCatalog(), "%", new String[]{"TABLE"});
			while (tableRs.next()) {
				String tableName = tableRs.getString("TABLE_NAME");

				if(!tableNameLst.contains(tableName)){
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
					List<TableIndex > exist = unqueIndices.stream().filter(o -> o.getTableName().equals(tableName) && o.getIndexName().equals(indexName)).collect(Collectors.toList());
					TableIndex index;
					if (exist != null && exist.size() > 0) {
						index = exist.get(0);
						index.addParams(idxRs.getString("COLUMN_NAME"));
					} else {
						index = new TableIndex();
						index.setTableName(tableName);
						index.setIndexName(indexName);
						index.setUnique(!idxRs.getBoolean("NON_UNIQUE"));
						index.addParams(idxRs.getString("COLUMN_NAME"));

						if(index.isUnique()){
							unqueIndices.add(index);
						}
					}
				}

				ResultSet columnRs = metaData.getColumns(connection.getCatalog(), connection.getCatalog(), tableName, "%");
				while (columnRs.next()) {
					TableColumn column = new TableColumn();
					String columnName = columnRs.getString("COLUMN_NAME");
					column.setName(columnName);
					column.setConvertName(Tool.lineToHump(column.getName()));

					column.setType(columnRs.getString("TYPE_NAME"));
					column.setJavaType(switchToJavaType(column.getType()));

					column.setLength(columnRs.getString("COLUMN_SIZE"));
					column.setDecimalDigits(columnRs.getString("DECIMAL_DIGITS"));
					column.setNotNull(!columnRs.getBoolean("NULLABLE"));
					column.setPrimary(column.getName().equals(primaryColumn));
					column.setComment(columnRs.getString("REMARKS"));
					columns.add(column);

					if(StrUtil.isNotBlank(primaryColumn) && primaryColumn.equalsIgnoreCase(columnName)){
						table.setPrimaryColumn(column);
					}
					if(CollectionUtil.isNotEmpty(unqueIndices) && unqueIndices.get(0).getColumns().contains(columnName)){
						if(table.getUniqueColumns() == null){
							List list = new ArrayList();
							list.add(column);
							table.setUniqueColumns(list);
						}else {
							table.getUniqueColumns().add(column);
						}
					}
				}
				tables.add(table);
			}
		}
		catch (SQLException throwables) {
			log.error("",throwables);
		}
		return tables;
	}

	public  void genJavaFiles(List<String> tableNameLst) {
		//实例化此前获取表和列信息的类，并获取信息
		List<TableInfo> tables = this.parseDb(tableNameLst);
		//实例化模板类，记得必须初始化
		FreeMarkerUtil freeMarkerUtil=new FreeMarkerUtil();
		freeMarkerUtil.init();
		//这是创建后的实体类存放地址
		String url="E://pojo//";
		for (TableInfo tt:tables) {
			//把获得的信息写入到指定模板内，开始生成实体类
			freeMarkerUtil.createFile("entity.ftl", url+tt.getClassName()+".java",tt);
		}
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
