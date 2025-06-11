package com.keepu.webAPI.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @org.springframework.beans.factory.annotation.Value("${cashflow.openapi.dev-url}")
    private String devUrl;

    @org.springframework.beans.factory.annotation.Value("${cashflow.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI keepuOpenAPI() {

        Server devServer= new Server()
                .url(devUrl)
                .description("Development server for KeepU API");

        Server prodServer = new Server()
                .url(prodUrl)
                .description("Production server for KeepU API");


        License license=new License().name("MIT LICENSE").url("https://opensource.org/license/mit/");

        return new OpenAPI()
                .info(new Info()
                        .title("KeepU API")
                        .description("API para la aplicación KeepU")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("KeepU Team")
                                .email("contacto@keepu.com")
                                .url("https://github.com/KeepU-Organization/")
                        )
                        .license(license)

                )
                .servers(List.of(devServer, prodServer));
    }


}
