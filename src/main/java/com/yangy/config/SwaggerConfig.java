package com.yangy.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger3配置信息
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    public Docket restApi() {
        // 所有的用户资源都放在这里
        return new Docket(DocumentationType.OAS_30).apiInfo(
                new ApiInfoBuilder()
                        .contact(new Contact("yangyu","","1819503703@qq.com"))
                        .title("学生可视化接口管理")
                        .build()
                );
    }


}
