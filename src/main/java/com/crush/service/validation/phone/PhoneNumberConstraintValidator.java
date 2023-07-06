package com.crush.service.validation.phone;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

public class PhoneNumberConstraintValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
    }

    @SneakyThrows
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return phoneNumberUtil.isPossibleNumber(s, "BE");
    }

}
