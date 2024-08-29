package com.company.project.springconfig;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {
	
	private static Logger logger = LoggerFactory.getLogger(CacheConfig.class);
	
	@Bean
	public CaffeineCacheManager memoryCacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager() {
			protected com.github.benmanes.caffeine.cache.Cache<Object, Object> createNativeCaffeineCache(String name) {
				logger.info("createNativeCaffeineCache() name:"+name);
				
				// 用于支持表达式创建Cache,示例: demo_cache_name:expireAfterWrite=10s,maximumSize=10000
				if(name.contains(":")) {
					String[] array = name.split(":");
					String cacheSpec = array[1];
					Caffeine caffeine = Caffeine.from(cacheSpec);
					return caffeine.build();
				}
				
				return super.createNativeCaffeineCache(name);
			}
		};
		
		Caffeine caffeine = Caffeine.newBuilder()
                //maximumSize用来控制cache的最大缓存数量
                .maximumSize(10000)
                .expireAfterWrite(Duration.ofMinutes(1))
                ;
		cacheManager.setCaffeine(caffeine);
		
//		cacheManager.setCacheNames(cacheNames);
		return cacheManager;
	}
	
}
