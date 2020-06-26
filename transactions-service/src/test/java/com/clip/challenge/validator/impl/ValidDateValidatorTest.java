package com.clip.challenge.validator.impl;

import com.clip.challenge.validator.ValidDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;

public class ValidDateValidatorTest {

    @InjectMocks
    private ValidDateValidator validator;
    @Mock
    ConstraintValidatorContext context;
    @Mock
    ConstraintValidatorContext.ConstraintViolationBuilder builder;
    @Mock
    ValidDate validDate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(this.validDate.pattern()).thenReturn("yyyy-MM-dd");
        this.validator.initialize(this.validDate);
    }

    @Test
    public void isValidTest() {
        Assertions.assertTrue(this.validator.isValid("2020-01-10", context));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"20-01-10", "10-10-2020", "2050-01-01"})
    public void isValidNegativeTest(String object) {
        Mockito.when(this.context.buildConstraintViolationWithTemplate(ArgumentMatchers.anyString()))
                .thenReturn(builder);
        Assertions.assertFalse(this.validator.isValid(object, context));
    }
}
