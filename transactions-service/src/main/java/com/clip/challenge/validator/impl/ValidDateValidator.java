package com.clip.challenge.validator.impl;

import com.clip.challenge.utils.DateUtils;
import com.clip.challenge.validator.ValidDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Slf4j
public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {

    private static final String DATE_IS_REQUIRED = "transactionDate is required";
    private static final String PAST_DATE = "transactionDate must be in the past";
    private String pattern;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if (StringUtils.isBlank(object)) {
            this.overrideDefaultConstraint(DATE_IS_REQUIRED, constraintContext);
            return false;
        }
        LocalDate date = DateUtils.parseDate(object, pattern);
        if (date == null) {
            return false;
        }
        if (date.isAfter(LocalDate.now())) {
            this.overrideDefaultConstraint(PAST_DATE, constraintContext);
            return false;
        }
        return true;
    }

    private void overrideDefaultConstraint(String message, ConstraintValidatorContext constraintContext) {
        constraintContext.disableDefaultConstraintViolation();
        constraintContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}