package com.btgpactual.technicaltest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullOrNotTextNullValidator implements ConstraintValidator<NotNullOrNotTextNull, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        };
        return !value.equalsIgnoreCase("null");
    };
    
};