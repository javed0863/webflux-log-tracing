package com.javedrpi.webfluxlogtracing.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@Configuration
public class SwaggerConfig {

    public static final String TITLE = "Spring Webflux Sample App";
    public static final String LICENCE_NAME = "MIT License";
    public static final String LICENSE_URL = "https://raw.githubusercontent.com/javed0863/webflux-log-tracing/main/LICENSE";
    public static final String BASIC_AUTH = "basicAuth";
    public static final String SECURITY_SCHEME_NAME = "my-api-security";
    public static final String BASIC_SECURITY_SCHEME = "basic";

    @Value("${app.security.jwt.enabled}")
    public boolean isJwtEnabled;

    @Bean
    public OpenAPI getSwaggerConfig(@Value("${application-description}") String appDesc,
                                 @Value("${application-version}") String appVersion){
        Components components = getComponents(isJwtEnabled);
        SecurityRequirement securityRequirement = getSecurityRequirement(isJwtEnabled);
        return new OpenAPI()
                .info(new Info()
                        .title(TITLE)
                        .version(appVersion)
                        .description(appDesc)
                        .license(new License()
                                .name(LICENCE_NAME)
                                .url(LICENSE_URL)
                        )
                        .contact(new Contact()
                                .name("Javed Ameen Shaiikh")
                                .url("https://www.javedrpi.com/me")
                                .email("javed0863@gmail.com")
                        )
                )
                .components(components)
                .addSecurityItem(securityRequirement);
    }

    private static SecurityRequirement getSecurityRequirement(boolean jwtFlag) {
        if(jwtFlag)
            return new SecurityRequirement().addList("bearerAuth");

        return new SecurityRequirement().addList(BASIC_AUTH);
    }

    private static Components getComponents(boolean jwtFlag) {
        SecurityScheme securityScheme = new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP);

        if(jwtFlag)
            return new Components().addSecuritySchemes("bearerAuth", securityScheme.scheme("bearer").bearerFormat("JWT"));

        return new Components().addSecuritySchemes(BASIC_AUTH, securityScheme.scheme(BASIC_SECURITY_SCHEME));
    }
}
