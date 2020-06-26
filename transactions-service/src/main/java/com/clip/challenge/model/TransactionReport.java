package com.clip.challenge.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionReport {

    private long userId;
    private String weekStartDate;
    private String weekFinishDate;
    private int quantity;
    private double amount;
    private double totalAmount;

}
