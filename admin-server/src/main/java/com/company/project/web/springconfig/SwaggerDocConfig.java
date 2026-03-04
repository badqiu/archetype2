/*
 * <<
 *  Davinci
 *  ==
 *  Copyright (C) 2016 - 2019 EDP
 *  ==
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  >>
 *
 */

package com.company.project.web.springconfig;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.project.AdminApplication;
import com.company.project.enums.Constant;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 文档默认访问地址: http://localhost:8080/demoproject-api/swagger-ui/index.html
 * 
 * http://localhost:8080/demoproject-api/swagger-ui.html
 */
@Configuration
@EnableOpenApi
@Profile({"dev","test","local"})  //只在特定环境激活文档
public class SwaggerDocConfig {
//	protected static final Logger log = LoggerFactory.getLogger(SwaggerDocConfig.class);
//    @Value("${server.port:8080}")
//    private String serverPort;
//    
//	@Value("${server.servlet.context-path:}")
//    private String serverServletContextPath;
//	
//    @Bean
//    public Docket createRestApiDoc() {
//
//    	log.info("createRestApiDoc serverServletContextPath:"+serverServletContextPath+ " serverPort:"+serverPort);
//        List<ResponseMessage> responseMessageList = new ArrayList<>();
////        responseMessageList.add(new ResponseMessageBuilder().code(HttpCodeEnum.SERVER_ERROR.getCode()).message(HttpCodeEnum.SERVER_ERROR.getMessage()).build());
//
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .globalResponseMessage(RequestMethod.GET, responseMessageList)
//                .globalResponseMessage(RequestMethod.POST, responseMessageList)
//                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
//                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
//                
////                .host("localhost:" + serverPort)
////                .pathMapping(serverServletContextPath)         // 设置上下文路径
//                .enable(Constant.SWAGGER_API_DOC_ENABLE)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(AdminApplication.class.getPackage().getName()))
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(Lists.newArrayList(apiKey()));
//
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("demoproject api")
//                .version("1.0")
//                .build();
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("Authorization", "token_username", "header");
//    }

}
