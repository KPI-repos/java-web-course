package com.example.javawebcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConfigurationPropertiesScan(basePackages = "com.example.javawebcourse")
public class JavaWebCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaWebCourseApplication.class, args);
    }

}
