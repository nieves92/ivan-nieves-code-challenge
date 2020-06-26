package com.clip.challenge.repository;

import com.clip.challenge.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    /*
    Commenting-out as part of code challenge to avoid logic delegation to DB.

    Optional<Transaction> findByTransactionIdAndUserId(String transactionId, long userId);

    Optional<List<Transaction>> findByUserId(long userId);
    */

}