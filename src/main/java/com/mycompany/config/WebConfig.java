package com.mycompany.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.mycompany.controller")
public class WebConfig implements WebMvcConfigurer {
	/**
	 * 配置视图解析器：前缀+视图返回值+后缀 == /index.jsp
	 * 
	 * @return
	 */
	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
		var viewResolver = new InternalResourceViewResolver("/", ".jsp");
		return viewResolver;
	}

	/**
	 * 配置multipart解析器
	 * 
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public MultipartResolver getMultipartResolver() {
		var multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxUploadSizePerFile(10485760);// 最大10M
		return multipartResolver;
	}

	/**
	 * 配置路径映射访问静态资源：解决url直接获取静态资源的问题
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//设置静态资源最高优先级，防止被拦截
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE).addResourceHandler("/static/**")
				.addResourceLocations("/static/").setCachePeriod(31556926);
	}

}
