package com.company.project.springconfig;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.project.enums.Constant;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {
	
	private static Logger logger = LoggerFactory.getLogger(CacheConfig.class);
	
	static int LOCAL_CACHE_MAXIMUM_SIZE = Constant.LOCAL_CACHE_MAXIMUM_SIZE;
	
	@Bean
	public CaffeineCacheManager memoryCacheManager() {
		CaffeineCacheManager cacheManager = new AutoCreateBySpecCaffeineCacheManager();
		
		Caffeine caffeine = Caffeine.newBuilder()
                //maximumSize用来控制cache的最大缓存数量
                .maximumSize(LOCAL_CACHE_MAXIMUM_SIZE)
                .expireAfterWrite(Duration.ofMinutes(1))
                ;
		cacheManager.setCaffeine(caffeine);
		
//		cacheManager.setCacheNames(cacheNames);
		return cacheManager;
	}
	
	public static class AutoCreateBySpecCaffeineCacheManager extends CaffeineCacheManager {
		private static Logger logger = LoggerFactory.getLogger(AutoCreateBySpecCaffeineCacheManager.class);
		protected com.github.benmanes.caffeine.cache.Cache<Object, Object> createNativeCaffeineCache(String name) {
			logger.info("createNativeCaffeineCache() name:"+name);
			
			// 用于支持表达式创建Cache,示例: demo_cache_name:expireAfterWrite=60s,maximumSize=10000
			if(name.contains(":")) {
				String[] array = name.split(":");
				String cacheSpec = array[1];
				Caffeine caffeine = Caffeine.from(cacheSpec);
				if(!cacheSpec.contains("maximumSize")) {
					caffeine.maximumSize(LOCAL_CACHE_MAXIMUM_SIZE);
				}
				return caffeine.build();
			}
			
			return super.createNativeCaffeineCache(name);
		}
	}
	
}
