package com.clip.challenge.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Slf4j
public class DateUtils {

    private DateUtils() {}

    public static LocalDate parseDate(String date, String pattern) {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            log.debug("Unable to parse date received {}", date);
        }
        return localDate;
    }

    public static LocalDate getEndDate(LocalDate date) {
        LocalDate localDate = date.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
        if (date.getMonth() != localDate.getMonth()) {
            localDate = date.with(TemporalAdjusters.lastDayOfMonth());
        }
        return localDate;
    }

    public static LocalDate getStartDate(LocalDate date) {
        LocalDate localDate = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        if (date.getMonth() != localDate.getMonth()) {
            localDate = date.with(TemporalAdjusters.firstDayOfMonth());
        }
        return localDate;
    }

    public static boolean isBetweenRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return date != null && !(date.isBefore(startDate) || date.isAfter(endDate));
    }

}
