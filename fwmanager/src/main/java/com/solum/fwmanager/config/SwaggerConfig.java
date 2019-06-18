package com.solum.fwmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	//@Value("${swagger.enable}")	
	private boolean	swaggerEnable = true;
	
	@Bean
    public Docket defaultDoc() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.enable(swaggerEnable)
        		.groupName("default")
        		.select()
        		.apis(RequestHandlerSelectors.basePackage("com.solum.fwmanager.controller"))
        		.paths(defaultPaths())
        		.build()
        		.apiInfo(getApiInfo())
        		.useDefaultResponseMessages(false)
        		.globalResponseMessage(RequestMethod.GET, 
            		Lists.newArrayList(
            			new ResponseMessageBuilder().code(204).message("Operation Succeeded. But There is no content.").build(),
            			new ResponseMessageBuilder().code(500).message("Fail to execute request because of internal error.").build()
            		)
            	);
    }

    private Predicate<String> defaultPaths() {
    	return 	PathSelectors.regex("/api.*");
    }
    
	private ApiInfo getApiInfo() {
		
		return new ApiInfoBuilder()
				.title("Star information API Documentation")
				.description("API to register/update/retrieve star information.")
				.build();
	}
}
