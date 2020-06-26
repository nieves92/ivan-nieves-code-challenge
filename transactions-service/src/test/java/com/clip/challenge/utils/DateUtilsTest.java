package com.clip.challenge.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.stream.Stream;

public class DateUtilsTest {

    @ParameterizedTest
    @MethodSource("provideValidScenarios")
    void parseDateTest(String date, String pattern) {
        Assertions.assertNotNull(DateUtils.parseDate(date, pattern));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidScenarios")
    void parseDateInvalidTest(String date, String pattern) {
        Assertions.assertNull(DateUtils.parseDate(date, pattern));
    }

    @Test
    void getEndDateTest() {
        LocalDate expectedDate = LocalDate.of(2020, Month.MAY, 14);
        LocalDate date = LocalDate.of(2020, Month.MAY, 13);
        Assertions.assertEquals(expectedDate, DateUtils.getEndDate(date));
    }

    @Test
    void getEndDateLastDayOfMonthTest() {
        LocalDate expectedDate = LocalDate.of(2020, Month.MAY, 31);
        LocalDate date = LocalDate.of(2020, Month.MAY, 30);
        Assertions.assertEquals(expectedDate, DateUtils.getEndDate(date));
    }

    @Test
    void getStartDateTest() {
        LocalDate expectedDate = LocalDate.of(2020, Month.MAY, 15);
        LocalDate date = LocalDate.of(2020, Month.MAY, 21);
        Assertions.assertEquals(expectedDate, DateUtils.getStartDate(date));
    }

    @Test
    void getStartDateFirstDayOfMonthTest() {
        LocalDate expectedDate = LocalDate.of(2020, Month.APRIL, 1);
        LocalDate date = LocalDate.of(2020, Month.APRIL, 2);
        Assertions.assertEquals(expectedDate, DateUtils.getStartDate(date));
    }

    @ParameterizedTest
    @MethodSource("provideDateInRange")
    void isBetweenRangeTest(LocalDate date, LocalDate startDate, LocalDate endDate) {
        Assertions.assertTrue(DateUtils.isBetweenRange(date, startDate, endDate));
    }

    @ParameterizedTest
    @MethodSource("provideDateNotInRange")
    void isBetweenRangeNegativeTest(LocalDate date, LocalDate startDate, LocalDate endDate) {
        Assertions.assertFalse(DateUtils.isBetweenRange(date, startDate, endDate));
    }

    private static Stream<Arguments> provideValidScenarios() {
        return Stream.of(
                Arguments.of("2020-03-18", "yyyy-MM-dd"),
                Arguments.of("18-03-2019", "dd-MM-yyyy"),
                Arguments.of("18-03-19", "dd-MM-yy")
        );
    }

    private static Stream<Arguments> provideInvalidScenarios() {
        return Stream.of(
                Arguments.of("invalidDate", "yyyy-MM-dd"),
                Arguments.of("20-12-01", "yyyy-MM-dd"),
                Arguments.of("18-3-2019", "dd-MM-yyyy"),
                Arguments.of("18-29-19", "dd-MM-yy")
        );
    }

    private static Stream<Arguments> provideDateInRange() {

        LocalDate startDate = LocalDate.of(2020, Month.APRIL, 2);
        LocalDate endDate = LocalDate.of(2020, Month.APRIL, 6);
        return Stream.of(
                Arguments.of(startDate, startDate, endDate),
                Arguments.of(startDate.plus(Period.ofDays(1)), startDate, endDate),
                Arguments.of(startDate.plus(Period.ofDays(2)), startDate, endDate),
                Arguments.of(startDate.plus(Period.ofDays(3)), startDate, endDate),
                Arguments.of(endDate, startDate, endDate)
        );
    }

    private static Stream<Arguments> provideDateNotInRange() {

        LocalDate startDate = LocalDate.of(2020, Month.APRIL, 2);
        LocalDate endDate = LocalDate.of(2020, Month.APRIL, 6);
        return Stream.of(
                Arguments.of(startDate.minus(Period.ofDays(1)), startDate, endDate),
                Arguments.of(endDate.plus(Period.ofDays(1)), startDate, endDate)
        );
    }
}
