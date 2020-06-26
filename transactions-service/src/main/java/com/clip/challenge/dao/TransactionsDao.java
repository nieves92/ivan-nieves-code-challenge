package com.clip.challenge.dao;

import com.clip.challenge.dto.TransactionDTO;
import com.clip.challenge.model.TransactionReport;

import java.util.List;

public interface TransactionsDao {

    /**
     * Adds a new transaction
     *
     * @param transactionDTO
     * @return transaction details
     */
    TransactionDTO addTransaction(TransactionDTO transactionDTO);

    /**
     * Retrieves a transaction based on the userId and the transactionId
     *
     * @param transactionId
     * @param userId
     * @return transaction details
     */
    TransactionDTO getTransaction(String transactionId, long userId);

    /**
     * Retrieves a random transaction
     *
     * @return transaction details
     */
    TransactionDTO getRandomTransaction();

    /**
     * Get the list of transactions for the provided user
     *
     * @param userId
     * @return list of transactions
     */
    List<TransactionDTO> getTransactionList(long userId);

    /**
     * Get the transactions report for the provided user
     *
     * @param userId
     * @return transactions report
     */
    List<TransactionReport> getTransactionReport(long userId);

    /**
     * Gets the total amount for all the transactions for the user
     *
     * @param userId
     * @return sum
     */
    double getSumForUser(long userId);
}
