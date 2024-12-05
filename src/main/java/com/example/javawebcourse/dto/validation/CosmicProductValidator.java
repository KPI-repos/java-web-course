package com.example.javawebcourse.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CosmicProductValidator implements ConstraintValidator<ValidCosmicProduct, String> {

    private static final String COSMIC_PRODUCT_PREFIX = "^Cosmic.*";

    private static final Pattern pattern = Pattern.compile(COSMIC_PRODUCT_PREFIX);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return pattern.matcher(value).matches();
    }
}
