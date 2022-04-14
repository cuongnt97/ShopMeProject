package com.shopme.admin.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     *
     * @param registry
     * @see ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "user-photos";
        Path userPhotoDir =  Paths.get("user-photos");
        String userPhotoPath = userPhotoDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:" + userPhotoPath + "/");

        String categoryImageName = "../category-images";
        Path categoryImageDir =  Paths.get(categoryImageName);
        String categoryImagePath = categoryImageDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/category-images/**")
                .addResourceLocations("file:" + categoryImagePath + "/");
    }
}
