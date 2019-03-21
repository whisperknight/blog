package com.mycompany.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

//	弃用的原因是通过getServletFilters设置的过滤器默认mappingUrl和dispatcherServlet是一样的
//	但我想拦截器拦截/*而serlvet匹配/实现restful风格的url
//	
//	
//	
//	/**
//	 * 配置过滤器，注：添加的每个filter都有一个默认的name。基于其类型，会被自动映射到DispatcherServlet。
//	 */
//	@Override
//	protected Filter[] getServletFilters() {
//		// 字符编码过滤器
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//
//		// shiro过滤器
//		DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy("shiroFilter");
//		shiroFilter.setTargetFilterLifecycle(true);
//
//		return new Filter[] { characterEncodingFilter, shiroFilter };
//	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

		// 字符编码过滤器
		var characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setBeanName("characterEncodingFilter");
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		this.registerServletFilter(servletContext, characterEncodingFilter)
				.addMappingForUrlPatterns(null, false, "/*");

		// shiro过滤器
		var shiroFilter = new DelegatingFilterProxy("shiroFilter");
		shiroFilter.setTargetFilterLifecycle(true);
		this.registerServletFilter(servletContext, shiroFilter).addMappingForUrlPatterns(null,
				false, "/*");
		
	}

	/**
	 * 配置Spring根容器
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	/**
	 * 配置Springweb容器
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	/**
	 * 配置
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };//设置restful风格url
	}
}
