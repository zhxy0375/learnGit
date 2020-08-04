package com.example.demo_cloud.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo_cloud.dto.req.TableParseReq;
import com.example.demo_cloud.dto.vo.TableColumn;
import com.example.demo_cloud.dto.vo.TableIndex;
import com.example.demo_cloud.dto.vo.TableInfo;
import com.example.demo_cloud.exception.DcCustomException;
import com.example.demo_cloud.util.FreeMarkerUtil;
import com.example.demo_cloud.util.SpringBeanContextHolder;
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
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MySqlDbService {

	/*@Resource(name = "secondDataSource")
	DataSource dataSource;*/

	@Resource(name = "dataSource")
	DataSource dataSource;

	public List<TableInfo> parseDb(TableParseReq req){
		List<TableInfo> tables = new ArrayList<>();
		if(CollectionUtil.isEmpty(req.getTableNames())){
			throw new DcCustomException("要解析的表名列表不能为空");
		}
		if(StrUtil.isBlank(req.getBasePackage())){
			throw new DcCustomException("基础包路径不能为空");
		}
		if(StrUtil.isBlank(req.getResponseClassPath())){
			throw new DcCustomException("Controller response类全路径不能为空");
		}

		DruidDataSource druidDataSource = SpringBeanContextHolder.getBean("dataSource", DruidDataSource.class);
				System.out.println("------------------------");
		for (Map.Entry<Object, Object> entry : druidDataSource.getConnectProperties().entrySet()) {
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		System.out.println("------------------------");

		List<TableIndex> unqueIndices = new ArrayList<>();
		try {

			Connection connection = dataSource.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();//see: https://www.cnblogs.com/dnn179/p/DatabaseMetaData.html

			ResultSet tableRs = metaData.getTables(connection.getCatalog(), connection.getCatalog(), "%", new String[]{"TABLE"});
			while (tableRs.next()) {
				String tableName = tableRs.getString("TABLE_NAME");

				if(!req.getTableNames().contains(tableName)){
					continue;
				}
				List<TableColumn> columns = new ArrayList<>();
				// 注释信息(COMMENT)时,发现返回的REMARKS字段返回居然是null.在数据连接url中添加了两个参数characterEncoding=utf8和useInformationSchema=true
				TableInfo table = new TableInfo(tableName, Tool.lineToHump(tableName), tableRs.getString("REMARKS"), columns);
				BeanUtil.copyProperties(req,table);

				List<String> primaryColumnList = new ArrayList<>();//主键 可能是组合主键
				ResultSet idxRs = metaData.getIndexInfo(connection.getCatalog(), connection.getCatalog(), tableName, false, false);
				while (idxRs.next()) {
					String indexName = idxRs.getString("INDEX_NAME");
					if(StrUtil.isBlank(indexName)){
						continue;//oracle 出现过 indexName 为null
					}
					if ("PRIMARY".equals(indexName)) {
						primaryColumnList.add(idxRs.getString("COLUMN_NAME"));
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

				List<String> columnJson = new ArrayList<>();
				ResultSet columnRs = metaData.getColumns(connection.getCatalog(), connection.getCatalog(), tableName, "%");
				while (columnRs.next()) {
					TableColumn column = new TableColumn();
					String columnName = columnRs.getString("COLUMN_NAME");
					column.setName(columnName);
					column.setConvertName(Tool.lineToHump(column.getName()));

					column.setType(columnRs.getString("TYPE_NAME"));
					column.setJavaType(switchToJavaType(column.getType()));

					if("Boolean".equals(column.getJavaType())){
						column.setGetterName("is"+Tool.upperFirstChar(column.getConvertName()));
					}else {
						column.setGetterName("get"+Tool.upperFirstChar(column.getConvertName()));
					}

					column.setLength(columnRs.getString("COLUMN_SIZE"));
					column.setDecimalDigits(columnRs.getString("DECIMAL_DIGITS"));
					column.setNotNull(!columnRs.getBoolean("NULLABLE"));
					column.setPrimary(primaryColumnList.contains(column.getName()));
					column.setComment(columnRs.getString("REMARKS"));
					columns.add(column);

					if(column.isPrimary()){
						if(table.getPrimaryColumns()  == null){
							table.setPrimaryColumns(new ArrayList<>());
						}
						table.getPrimaryColumns().add(column);
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

					if("Long".equals(column.getJavaType()) || "Integer".equals(column.getJavaType()) || "BigDecimal".equals(column.getJavaType())){
						columnJson.add("\""+ column.getConvertName()+"\":0");
					}else if("Boolean".equals(column.getJavaType()) ){
						columnJson.add("\""+ column.getConvertName()+"\":false");
					}else {
						columnJson.add("\""+ column.getConvertName()+"\":\"str\"");
					}
				}

				table.setEntityJson("{" + String.join(",",columnJson) +"}");
				tables.add(table);
			}
		}
		catch (SQLException throwables) {
			log.error("",throwables);
		}
		return tables;
	}

	public  void genJavaFiles(TableParseReq req) {
		//实例化此前获取表和列信息的类，并获取信息
		List<TableInfo> tables = this.parseDb(req);
		//实例化模板类，记得必须初始化
		FreeMarkerUtil freeMarkerUtil=new FreeMarkerUtil();
		freeMarkerUtil.init();
		//这是创建后的实体类存放地址
		String url="E://pojo//";
		for (TableInfo tt:tables) {
			//把获得的信息写入到指定模板内，开始生成实体类
			freeMarkerUtil.createFile("entity.ftl", url+tt.getClassName()+".java",tt);
			freeMarkerUtil.createFile("service.ftl", url+tt.getClassName()+"Service.java",tt);
			freeMarkerUtil.createFile("mapper.ftl", url+tt.getClassName()+"Mapper.java",tt);
			freeMarkerUtil.createFile("mapper.xml.ftl", url+tt.getClassName()+"Mapper.xml",tt);
			freeMarkerUtil.createFile("controller.ftl", url+tt.getClassName()+"Controller.java",tt);
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


	public static void main(String[] args) {
		try {
			Properties props =new Properties();

			props.put("remarksReporting","true");
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String user="fms";
			String password="fms";
			if (user != null) {
				props.put("user", user);
			}
			if (password != null) {
				props.put("password", password);
			}
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@10.201.128.108:1521:test", props);
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet tableRs = metaData.getTables(connection.getCatalog(), connection.getCatalog(), "%", new String[]{"TABLE"});
			while (tableRs.next()) {
				String tableName = tableRs.getString("TABLE_NAME");
				String remark = tableRs.getString("REMARKS");
				System.out.println(tableName + "########"+remark);
			}
		}catch (Exception e ){

		}


	}


}
