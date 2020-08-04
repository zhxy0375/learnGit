package com.example.demo_cloud.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanContextHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public SpringBeanContextHolder() {
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringBeanContextHolder.applicationContext = applicationContext;
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		return applicationContext.getBean(beanName, clazz);
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
}
