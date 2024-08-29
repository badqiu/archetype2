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
