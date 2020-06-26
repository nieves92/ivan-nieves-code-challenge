package com.clip.challenge.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class DateRange implements Comparable<DateRange> {

    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public int compareTo(DateRange o) {
        return this.startDate.compareTo(o.startDate);
    }
}
