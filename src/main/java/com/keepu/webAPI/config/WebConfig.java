package com.keepu.webAPI.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/profilePics/";

    @PostConstruct
    public void init() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (created) {
                System.out.println("Carpeta creada: " + UPLOAD_DIR);
            } else {
                System.err.println("No se pudo crear la carpeta: " + UPLOAD_DIR);
            }
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Permite acceder a http://localhost:8080/uploads/profilePics/imagen.jpg
        registry.addResourceHandler("/uploads/profilePics/**")
                .addResourceLocations("file:" + UPLOAD_DIR);
    }
}
