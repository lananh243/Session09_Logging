package com.ra.ss9.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class ConfirmPasswordMatchingValidator implements ConstraintValidator<ConfirmPasswordMatching, Object> {
    String password;
    String confirmPassword;

    @Override
    public void initialize(ConfirmPasswordMatching constraintAnnotation) {
        password = constraintAnnotation.password();
        confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
        Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmPassword);
        return Objects.equals(passwordValue, confirmPasswordValue);
    }
}