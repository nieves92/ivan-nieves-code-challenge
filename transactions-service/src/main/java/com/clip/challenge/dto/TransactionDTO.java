package com.clip.challenge.dto;

import com.clip.challenge.validator.ValidDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;

@Getter
@Setter
public class TransactionDTO {

    @DecimalMin(value = "0.01", message = "Amount is required")
    private double amount;
    private String transactionId;
    private String description;
    @JsonProperty("date")
    @ValidDate
    private String transactionDate;
    private long userId;

}
