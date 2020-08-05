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
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
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
			String driverName = metaData.getDriverName();
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

				//为统一oracle mysql 用getPrimaryKeys 取主键，而不用 遍历index的方式
				ResultSet prKeyRs = metaData.getPrimaryKeys(connection.getCatalog(), connection.getCatalog(), tableName);
				while (prKeyRs.next()){
					primaryColumnList.add(prKeyRs.getString("COLUMN_NAME"));
				}
				ResultSet idxRs = metaData.getIndexInfo(connection.getCatalog(), connection.getCatalog(), tableName, false, false);
				while (idxRs.next()) {
					String indexName = idxRs.getString("INDEX_NAME");
					if(StrUtil.isBlank(indexName)){
						continue;//oracle 出现过 indexName 为null
					}
					//MySql 会多出 PRIMARY 的index; oracle没有
					if ("PRIMARY".equals(indexName)) {
//						primaryColumnList.add(idxRs.getString("COLUMN_NAME"));
						continue;
					}
					/*//排除
					else if(primaryColumnList.contains(idxRs.getString("COLUMN_NAME"))){
						continue;
					}*/

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
				//最终挑选出一个 唯一索引
				TableIndex uniqueIndexFinal = null;
				if(CollectionUtil.isNotEmpty(unqueIndices)){
					//主键 字符串
					String primaryJson = "";
					if(CollectionUtil.isNotEmpty(primaryColumnList)){
						List<String> lstPr = primaryColumnList.stream().sorted().collect(Collectors.toList());
						primaryJson = String.join(",",lstPr);
					}
					for (TableIndex uniqueIndex : unqueIndices) {
						List<String> lstUn = uniqueIndex.getColumns().stream().sorted().collect(Collectors.toList());
						String unJson = String.join(",",lstUn);//唯一键字符串
						if(primaryJson.equalsIgnoreCase(unJson)){
							continue;
						}else {
							uniqueIndexFinal = uniqueIndex;
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

					column.setLength(columnRs.getInt("COLUMN_SIZE"));
					column.setDecimalDigits(columnRs.getInt("DECIMAL_DIGITS"));

					column.setJavaImportType(switchToJavaType(driverName,column.getType(),column.getLength(),column.getDecimalDigits()));
					column.setJavaType(Tool.geStrAfterSplit(column.getJavaImportType(),"."));

					if("Boolean".equals(column.getJavaType())){
						column.setGetterName("is"+Tool.upperFirstChar(column.getConvertName()));
					}else {
						column.setGetterName("get"+Tool.upperFirstChar(column.getConvertName()));
					}

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
					if(uniqueIndexFinal != null && uniqueIndexFinal.getColumns().contains(columnName)){
						if(table.getUniqueColumns() == null){
							List list = new ArrayList();
							list.add(column);
							table.setUniqueColumns(list);
						}else {
							table.getUniqueColumns().add(column);
						}
					}

					//java.lang 下的包 会自动导入 不用额外导入
					if(!column.getJavaImportType().startsWith("java.lang")){
						if(table.getColumnImports() == null){
							Set<String> set = new HashSet<>();
							set.add(column.getJavaImportType());
							table.setColumnImports(set);
						}else {
							table.getColumnImports().add(column.getJavaImportType());
						}
					}

					if("Short".equals(column.getJavaType())  || "Long".equals(column.getJavaType()) || "Integer".equals(column.getJavaType()) || "BigDecimal".equals(column.getJavaType())){
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

	public static String switchToJavaType(String driverName,String columnType,int length,int decimalDigits) {
		if(driverName.toLowerCase().indexOf("oracle") >=0){
			return switchOracleToJavaType(columnType,length, decimalDigits);
		}else {
			return switchMySqlToJavaType(columnType,length, decimalDigits);
		}

	}

	//把数据库类型转换成java类型
	public static String switchMySqlToJavaType(String cloumnType,int length,int decimalDigits) {
		String fieldType = null;

		if (
				cloumnType.equals("VARCHAR")
						|| cloumnType.equals("CHAR")
						|| cloumnType.equals("TEXT")
						|| cloumnType.equals("VARCHAR") ) {
			fieldType = String.class.getCanonicalName();
		} else if (cloumnType.equals("BIGINT")) {
			fieldType = Long.class.getCanonicalName();
		} else if (
				cloumnType.equals("INT")
						|| cloumnType.equals("TINYINT")
						|| cloumnType.equals("SMALLINT")
						|| cloumnType.equals("MEDIUMINT") ) {
			fieldType = Integer.class.getCanonicalName();
		}  else if (cloumnType.equals("BIT")) {
			fieldType = Boolean.class.getCanonicalName();
		}else if (
				cloumnType.equals("FLOAT")
						|| cloumnType.equals("DOUBLE")
						|| cloumnType.equals("DECIMAL") ) {
			fieldType = BigDecimal.class.getCanonicalName();
		}else if (
				cloumnType.equals("DATETIME")
						|| cloumnType.equals("DATE")
						|| cloumnType.equals("TIMESTAMP") ) {
			fieldType = Date.class.getCanonicalName();
		} else {
			fieldType = String.class.getCanonicalName();
		}
		return fieldType;
	}

	//把数据库类型转换成java类型
	public static String switchOracleToJavaType (String columnType,int length,int decimalDigits) {
		String fieldType = null;

		if ( columnType.indexOf("CHAR") >=0
				|| columnType.equalsIgnoreCase("LONG")) {
			fieldType = String.class.getCanonicalName();
		} else if (columnType.equals("BIGINT")) {
			fieldType = Long.class.getCanonicalName();
		} else if (
				columnType.equals("INT")
						|| columnType.equals("TINYINT")
						|| columnType.equals("SMALLINT")
						|| columnType.equals("MEDIUMINT") ) {
			fieldType = Integer.class.getCanonicalName();
		}  else if (columnType.equals("BIT")) {
			fieldType = Boolean.class.getCanonicalName();
		}else if (decimalDigits == 0 && columnType.equals("NUMBER")) {
			if(length == 1){
				fieldType = Boolean.class.getCanonicalName();
			} else if(length < 5){
				fieldType =  Short.class.getCanonicalName();
			} else if(length >= 5 && length <= 9){
				fieldType = Integer.class.getCanonicalName();
			}  else if(length >= 10 && length <= 18){
				fieldType = Long.class.getCanonicalName();
			} else if(length >= 19){
				fieldType = BigDecimal.class.getCanonicalName();
			}
		} else if (decimalDigits > 0 && columnType.equals("NUMBER")) {
			fieldType = BigDecimal.class.getCanonicalName();
		}else if (
				columnType.equals("FLOAT")
						|| columnType.equals("DOUBLE")
						|| columnType.equals("DECIMAL")
						|| columnType.equals(JDBCType.REAL.getName())
		) {
			fieldType = BigDecimal.class.getCanonicalName();
		}else if ( columnType.equals("DATE")
				|| columnType.equals(JDBCType.TIME)
				|| columnType.indexOf("TIMESTAMP") >=0
		) {
			fieldType = Date.class.getCanonicalName();
		} else {
			fieldType = String.class.getCanonicalName();
		}
		return fieldType;
	}

	/*public static void main(String[] args) {
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
	}*/


	public static void main(String[] args) {
		System.out.println(BigDecimal.class.getSimpleName());
		System.out.println(BigDecimal.class.getName());
		System.out.println(BigDecimal.class.getTypeName());
		System.out.println(BigDecimal.class.getCanonicalName());
		System.out.println(String[].class.getName());
		System.out.println(String[].class.getTypeName());
		System.out.println(String[].class.getCanonicalName());
		System.out.println(String[].class.getSimpleName());
	}
}
