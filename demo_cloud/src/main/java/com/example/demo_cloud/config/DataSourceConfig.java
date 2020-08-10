package com.example.demo_cloud.config;

//DruidDataSourceAutoConfigure


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
	// 精确到 master 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.example.demo_cloud.dao.one";
//	static final String MAPPER_LOCATION = "classpath:mapper/one/*.xml";
	static final String MAPPER_LOCATION = "classpath*:com/example/demo_cloud/dao/one/*.xml";

/*	@Bean(name = "dataSource")
//	@ConfigurationProperties("spring.datasource.druid.one")
	public DataSource dataSource(@Qualifier("druidDataSource") DataSource dataSource){
		DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
//		System.out.println("------------------------");
//		for (Map.Entry<Object, Object> entry : druidDataSource.getConnectProperties().entrySet()) {
//			System.out.println(entry.getKey()+":"+entry.getValue());
//		}
//		System.out.println("------------------------");
		//了解一下  为什么 apollo 配置进不来？

//		druidDataSource.getConnectProperties().setProperty("remarksReporting","true");
		return druidDataSource;
	}*/


	@Bean(name = "dataSource")
	@ConfigurationProperties("spring.datasource.druid.one")
	public DruidDataSource druidDataSource (){
		DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
//		System.out.println("------------------------");
//		for (Map.Entry<Object, Object> entry : druidDataSource.getConnectProperties().entrySet()) {
//			System.out.println(entry.getKey()+":"+entry.getValue());
//		}
//		System.out.println("------------------------");
		//了解一下  为什么 apollo 配置进不来？

//		druidDataSource.getConnectProperties().setProperty("remarksReporting","true");
		return druidDataSource;
	}


	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(druidDataSource());
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources(DataSourceConfig.MAPPER_LOCATION));

//		//https://pagehelper.github.io/
//		//PageHelper 支持不同类型数据库多数据  https://www.jianshu.com/p/ac8a795fc777
//		//mybatis分页
//		Properties props = new Properties();
//		props.setProperty("helperDialect", "mysql");  //注意 pageHelper 版本问题：dialect
////		# 如果启用，当pagenum<1时，会自动查询第一页的数据，当pagenum>pages时，自动查询最后一页数据；
////      # 不启用的，以上两种情况都会返回空数据
//		props.setProperty("reasonable", "false");

//		PageInterceptor pageHelper = new PageInterceptor();
//		pageHelper.setProperties(props);
//		//添加插件
//		sessionFactory.setPlugins(new Interceptor[]{pageHelper});

		return sessionFactory.getObject();
	}

}
