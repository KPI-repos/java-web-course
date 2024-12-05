package com.example.javawebcourse.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CosmicProductValidator.class)
@Documented
public @interface ValidCosmicProduct {

    String message() default "Invalid cosmic product name. The name should start with 'Cosmic'. Example: 'Cosmic Gem'.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
