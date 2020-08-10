package com.example.demo_cloud.config;

//DruidDataSourceAutoConfigure


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
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
import java.util.Properties;

@Configuration
@MapperScan(basePackages = DataSourceSecConfig.PACKAGE, sqlSessionFactoryRef = "secondSqlSessionFactory")
public class DataSourceSecConfig {
	// 精确到 master 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.example.demo_cloud.dao.two";
	static final String ENTITY_PACKAGE = "com.example.demo_cloud.dao.entity";

	/*classpath：只会到你的class路径中查找找文件;
	classpath*：不仅包含class路径，还包括jar文件中(class路径)进行查找*/
//	static final String MAPPER_LOCATION = "classpath:mapper/two/*.xml";
static final String MAPPER_LOCATION = "classpath*:com/example/demo_cloud/dao/two/*.xml";

	@Bean(name = "secondDataSource")
	@ConfigurationProperties("spring.datasource.druid.two")
	public DataSource dataSource(){
		return DruidDataSourceBuilder.create().build();
	}


	@Bean(name = "secondTransactionManager")
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}


	@Bean(name = "secondSqlSessionFactory")
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources(DataSourceSecConfig.MAPPER_LOCATION));
		sessionFactory.setTypeAliasesPackage(ENTITY_PACKAGE);

//		//https://pagehelper.github.io/
//		//PageHelper 支持不同类型数据库多数据  https://www.jianshu.com/p/ac8a795fc777
//		//mybatis分页
//		Properties props = new Properties();
//		props.setProperty("helperDialect", "oracle");
//		props.setProperty("reasonable", "false");
//
//		PageInterceptor pageHelper = new PageInterceptor();
//		pageHelper.setProperties(props);
//		//添加插件
//		sessionFactory.setPlugins(new Interceptor[]{pageHelper});

		return sessionFactory.getObject();
	}

}
