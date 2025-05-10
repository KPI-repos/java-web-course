package com.example.javawebcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class JavaWebCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaWebCourseApplication.class, args);
    }

}
