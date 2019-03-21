package com.mycompany.config;

import java.beans.PropertyVetoException;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.mycompany.realm.MyRealm;

@Configuration
@PropertySource("classpath:/blog.properties")
@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan("com.mycompany.dao")
@ComponentScan("com.mycompany.service")
public class RootConfig implements EnvironmentAware {
	/**
	 * 注入环境属性
	 */
	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	/**
	 * 配置Druid数据源
	 * 
	 * @return
	 * @throws PropertyVetoException
	 */
	@Bean(name = "dataSource", destroyMethod = "close")
	public DruidDataSource getDataSource() throws PropertyVetoException {
		var ds = new DruidDataSource();

		ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		ds.setUrl(env.getProperty("jdbc.url"));
		ds.setUsername(env.getProperty("jdbc.username"));
		ds.setPassword(env.getProperty("jdbc.password"));

		return ds;
	}

	/**
	 * 配置mybatis的sqlSessionFactory
	 * 
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean("sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(@Value("#{dataSource}") DataSource dataSource)
			throws Exception {
		var factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath:com/mycompany/mappers/*.xml"));
		factoryBean.setTypeAliasesPackage("com.mycompany.entity");
		return factoryBean.getObject();
	}

	/**
	 * 配置事务管理：JDBC事物管理
	 * 
	 * @param ds
	 * @return
	 */
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager getTransactionManager(
			@Value("#{dataSource}") DataSource dataSource) {
		var tm = new DataSourceTransactionManager(dataSource);
		return tm;
	}

	/*******************************************************************/
	/** 下面是shiro的配置 **/
	/*******************************************************************/

	// 自定义Realm
	@Bean("myRealm")
	public MyRealm getMyRealm() {
		return new MyRealm();
	}

	// 安全管理器
	@Bean("securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(
			@Value("#{myRealm}") MyRealm myRealm) {
		return new DefaultWebSecurityManager(myRealm);
	}

	// Shiro过滤器
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(
			@Value("#{securityManager}") DefaultWebSecurityManager securityManager) {
		var shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/blogger/login");// 设置身份认证失败的跳转页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/blogger/login");// 设置授权认证失败的跳转页面
		var map = new HashMap<String, String>();
//		map.put("/login", "anon");//访问login无须验证
		map.put("/admin/**", "roles[blogger]");// 访问admin，会先身份验证，再角色授权验证
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//		shiroFilterFactoryBean.setFilterChainDefinitions("/login=anon\n/admin/**=authc");
		return shiroFilterFactoryBean;
	}

	// 保证实现了Shiro内部lifecycle函数的bean执行
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	// 开启Shiro注解
	@Bean("defaultAdvisorAutoProxyCreator")
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}

	// shiro授权属性源通知
	@Bean("authorizationAttributeSourceAdvisor")
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
			@Value("#{securityManager}") DefaultWebSecurityManager securityManager) {
		var authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
