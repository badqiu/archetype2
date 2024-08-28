package com.company.project.springconfig;

import java.time.Duration;

import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {

	@Bean
	public CaffeineCacheManager caffeineCacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		
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
