package com.clip.challenge.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTIONS_TABLE")
@Getter
@Setter
public class Transaction {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    private double amount;
    private String transactionId;
    private String description;
    private String transactionDate;
    private long userId;

}
