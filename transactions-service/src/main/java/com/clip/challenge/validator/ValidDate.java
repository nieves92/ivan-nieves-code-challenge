package com.clip.challenge.validator;

import com.clip.challenge.validator.impl.ValidDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.clip.challenge.constants.AppConstants.DEFAULT_DATE_PATTERN;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
@Documented
public @interface ValidDate {

    String message() default "transactionDate provided is not in valid format YYYY-MM-DD.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern() default DEFAULT_DATE_PATTERN;

}