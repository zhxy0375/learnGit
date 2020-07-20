package com.example.demo_cloud.config;

//DruidDataSourceAutoConfigure


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
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

@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
	// 精确到 master 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.example.demo_cloud.dao.one";
//	static final String MAPPER_LOCATION = "classpath:mapper/one/*.xml";
	static final String MAPPER_LOCATION = "classpath*:com/example/demo_cloud/dao/one/*.xml";

	@Bean(name = "dataSource")
	@ConfigurationProperties("spring.datasource.druid.one")
	public DataSource dataSource(){
		return DruidDataSourceBuilder.create().build();
	}


	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources(DataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}

}
