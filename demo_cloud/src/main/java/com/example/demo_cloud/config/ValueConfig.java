package com.example.demo_cloud.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@EnableApolloConfig("jdbc")
public class ValueConfig {

	@Value("${jdbc.url:test}")
	private String jdbcUrl;

}
