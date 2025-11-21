package com.company.project.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 线程池配置
 **/
@Configuration
public class ThreadPoolConfig {

    @Bean
    @Primary
    public ThreadPoolExecutor threadPoolExecutor(){

        return new ThreadPoolExecutor( 200,
                1000,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),
                Executors.defaultThreadFactory(),
                //直接自己调用
                new ThreadPoolExecutor.CallerRunsPolicy());

    }
    
}