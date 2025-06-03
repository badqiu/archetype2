package com.company.project.springconfig;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import com.github.jeffreyning.mybatisplus.conf.EnableKeyGen;
//import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
//
//import org.apache.ibatis.plugin.Interceptor;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
//import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
//import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
/**
* 
* Mybatis配置管理类
* @author lvmingzhao
* @since 2019-11-06
*
*/
@MapperScan(basePackages="com.company.project.mapper")
//@EnableConfigurationProperties(MybatisPlusProperties.class)
@Configuration
//@EnableMPP
//@EnableKeyGen
public class MybatisPlusConfig {
//	
//	@Autowired
//	private DataSource demoprojectDataSource;
//
//	@Autowired(required = false)
//	private Interceptor[] interceptors;
//	
//	@Autowired
//	private MybatisPlusProperties properties;
//	
//    /**
//     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
//     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }
//   
	
	/**
	 *	 mybatis-plus分页插件
	 */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());//如果配置多个插件,切记分页最后添加
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); 如果有多数据源可以不配具体类型 否则都建议配上具体的DbType
        return interceptor;
    }
	
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }
    
//	/**
//	 * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
//	 * 配置文件和mybatis-boot的配置文件同步
//	 * @return
//	 */
//	@Bean
//	public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
//		MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
//		mybatisPlus.setDataSource(demoprojectDataSource);
//		mybatisPlus.setConfiguration(properties.getConfiguration());
//		if (!ObjectUtils.isEmpty(this.interceptors)) {
//			mybatisPlus.setPlugins(this.interceptors);
//		}
//		MybatisConfiguration mc = new MybatisConfiguration();
//		// 对于完全自定义的mapper需要加此项配置，才能实现下划线转驼峰
//		mc.setMapUnderscoreToCamelCase(true);
//		mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
//		mc.setObjectWrapperFactory(new MybatisMapWrapperFactory());
//		mybatisPlus.setConfiguration(mc);
//		if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
//			mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
//		}
//		if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
//			mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
//		}
//		if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
//			mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
//		}
//		return mybatisPlus;
//	}
    
}