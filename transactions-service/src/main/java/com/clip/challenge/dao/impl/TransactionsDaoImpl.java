package com.clip.challenge.dao.impl;

import com.clip.challenge.builder.impl.TransactionBuilderImpl;
import com.clip.challenge.dao.TransactionsDao;
import com.clip.challenge.dto.TransactionDTO;
import com.clip.challenge.entity.Transaction;
import com.clip.challenge.exception.NotFoundException;
import com.clip.challenge.model.DateRange;
import com.clip.challenge.model.TransactionReport;
import com.clip.challenge.repository.TransactionsRepository;
import com.clip.challenge.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.clip.challenge.constants.AppConstants.DEFAULT_DATE_PATTERN;

@Service
public class TransactionsDaoImpl implements TransactionsDao {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    TransactionBuilderImpl transactionBuilderImpl;

    @Override
    public TransactionDTO addTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = this.transactionBuilderImpl.buildEntity(transactionDTO);
        transaction.setTransactionId(String.valueOf(UUID.randomUUID()));
        transaction = this.transactionsRepository.save(transaction);
        return this.transactionBuilderImpl.buildDto(transaction);
    }

    @Override
    public TransactionDTO getTransaction(String transactionId, long userId) {
        return this.findByTransactionIdAndUserId(transactionId, userId)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public TransactionDTO getRandomTransaction() {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        if (CollectionUtils.isEmpty(transactionList)) {
            throw new NotFoundException();
        }
        Collections.shuffle(transactionList);
        return this.transactionBuilderImpl.buildDto(transactionList.get(0));
    }

    @Override
    public List<TransactionDTO> getTransactionList(long userId) {
        return this.findByUserId(userId)
                .orElse(new ArrayList<>());
    }

    @Override
    public List<TransactionReport> getTransactionReport(long userId) {
        Optional<List<TransactionDTO>> optionalList = this.findByUserId(userId);
        if (!optionalList.isPresent() || CollectionUtils.isEmpty(optionalList.get())) {
            return new ArrayList<>();
        }
        List<TransactionReport> transactionReportList = new ArrayList<>();
        List<TransactionDTO> transactionList = optionalList.get();
        Set<DateRange> dateRangeSet = this.getDateRanges(transactionList);
        double totalAmount = 0d;
        for (DateRange dateRange : dateRangeSet) {
            double amount = 0d;
            int quantity = 0;
            for (TransactionDTO transaction : transactionList) {
                LocalDate transactionDate = DateUtils.parseDate(transaction.getTransactionDate(), DEFAULT_DATE_PATTERN);
                if (DateUtils.isBetweenRange(transactionDate, dateRange.getStartDate(), dateRange.getEndDate())) {
                    quantity++;
                    amount += transaction.getAmount();
                }
            }
            if (quantity > 0) {
                TransactionReport transactionReport = new TransactionReport();
                transactionReport.setUserId(userId);
                transactionReport.setWeekStartDate(String.valueOf(dateRange.getStartDate()));
                transactionReport.setWeekFinishDate(String.valueOf(dateRange.getEndDate()));
                transactionReport.setQuantity(quantity);
                transactionReport.setAmount(amount);
                transactionReport.setTotalAmount(totalAmount);
                transactionReportList.add(transactionReport);
            }
            totalAmount += amount;
        }
        return transactionReportList;
    }

    @Override
    public double getSumForUser(long userId) {
        Optional<List<TransactionDTO>> transactionList = this.findByUserId(userId);
        if (!transactionList.isPresent() || CollectionUtils.isEmpty(transactionList.get())) {
            return 0;
        }
        return transactionList.get().stream()
                .filter(Objects::nonNull)
                .map(TransactionDTO::getAmount)
                .reduce(0d, (a, b) -> a + b);
    }

    private Optional<TransactionDTO> findByTransactionIdAndUserId(String transactionId, long userId) {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        if (CollectionUtils.isEmpty(transactionList)) {
            return Optional.empty();
        }
        return Optional.ofNullable(transactionList.stream()
                .filter(Objects::nonNull)
                .filter(t -> t.getUserId() == userId)
                .filter(t -> t.getTransactionId() != null && t.getTransactionId().equals(transactionId))
                .findFirst()
                .map(this.transactionBuilderImpl::buildDto)
                .orElse(null));
    }

    private Optional<List<TransactionDTO>> findByUserId(long userId) {
        List<Transaction> transactionList = this.transactionsRepository.findAll();
        if (CollectionUtils.isEmpty(transactionList)) {
            return Optional.empty();
        }
        return Optional.of(transactionList.stream()
                .filter(Objects::nonNull)
                .filter(t -> t.getUserId() == userId)
                .map(this.transactionBuilderImpl::buildDto)
                .collect(Collectors.toList()));
    }

    private Set<DateRange> getDateRanges(List<TransactionDTO> transactionList) {
        SortedSet<DateRange> dateRanges = new TreeSet<>();
        transactionList.stream()
                .map(TransactionDTO::getTransactionDate)
                .filter(Objects::nonNull)
                .forEach(td -> {
                    LocalDate date = DateUtils.parseDate(td, DEFAULT_DATE_PATTERN);
                    if (date == null) {
                        return;
                    }
                    LocalDate startDate;
                    LocalDate endDate;
                    switch (date.getDayOfWeek()) {
                        case THURSDAY:
                            startDate = DateUtils.getStartDate(date);
                            endDate = date;
                            break;
                        case FRIDAY:
                            startDate = date;
                            endDate = DateUtils.getEndDate(date);
                            break;
                        default:
                            startDate = DateUtils.getStartDate(date);
                            endDate = DateUtils.getEndDate(date);
                    }
                    dateRanges.add(new DateRange(startDate, endDate));
                });
        return dateRanges;
    }

}
