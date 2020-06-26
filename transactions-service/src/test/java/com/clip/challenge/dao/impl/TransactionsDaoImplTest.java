package com.clip.challenge.dao.impl;

import com.clip.challenge.builder.impl.TransactionBuilderImpl;
import com.clip.challenge.dto.TransactionDTO;
import com.clip.challenge.entity.Transaction;
import com.clip.challenge.exception.NotFoundException;
import com.clip.challenge.model.TransactionReport;
import com.clip.challenge.repository.TransactionsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TransactionsDaoImplTest {

    @InjectMocks
    private TransactionsDaoImpl transactionsDao;
    @Mock
    TransactionsRepository transactionsRepository;
    @Mock
    TransactionBuilderImpl transactionBuilderImpl;
    @Mock
    private TransactionDTO transactionDTO;
    @Mock
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTransactionTest() {
        Mockito.when(this.transactionBuilderImpl.buildEntity(ArgumentMatchers.eq(this.transactionDTO)))
                .thenReturn(this.transaction);
        Mockito.when(this.transactionsRepository.save(ArgumentMatchers.eq(this.transaction)))
                .thenReturn(this.transaction);
        Mockito.when(this.transactionBuilderImpl.buildDto(ArgumentMatchers.eq(this.transaction)))
                .thenReturn(this.transactionDTO);

        Assertions.assertNotNull(this.transactionsDao.addTransaction(this.transactionDTO));
    }

    @Test
    public void getTransactionTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(Arrays.asList(this.transaction, this.transaction));
        Mockito.when(this.transaction.getUserId()).thenReturn(123l);
        Mockito.when(this.transaction.getTransactionId())
                .thenReturn("3afb7f7c-00e6-4812-a737-500d9e8b4b12")
                .thenReturn("9a587cfe-70bd-4118-a23c-6bbe30bd1eb0");
        Mockito.when(this.transactionBuilderImpl.buildDto(ArgumentMatchers.eq(this.transaction)))
                .thenReturn(this.transactionDTO);
        Assertions.assertNotNull(this.transactionsDao.getTransaction("9a587cfe-70bd-4118-a23c-6bbe30bd1eb0", 123l));
    }

    @ParameterizedTest
    @MethodSource("provideNotMatchingCases")
    public void getTransactionNotMatchingTest(String transactionId, long userId) {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(Arrays.asList(this.transaction, this.transaction));
        Mockito.when(this.transaction.getUserId()).thenReturn(123l);
        Mockito.when(this.transaction.getTransactionId())
                .thenReturn("3afb7f7c-00e6-4812-a737-500d9e8b4b12")
                .thenReturn("9a587cfe-70bd-4118-a23c-6bbe30bd1eb0");
        Mockito.when(this.transactionBuilderImpl.buildDto(ArgumentMatchers.eq(this.transaction)))
                .thenReturn(this.transactionDTO);
        Assertions.assertThrows(NotFoundException.class,
                () -> this.transactionsDao.getTransaction(transactionId, userId));
    }

    @Test
    public void getTransactionNoTransactionsTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NotFoundException.class,
                () -> this.transactionsDao.getTransaction("1234", 123l));
    }

    @Test
    public void getRandomTransactionTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(Arrays.asList(this.transaction, this.transaction));
        Mockito.when(this.transactionBuilderImpl.buildDto(ArgumentMatchers.eq(this.transaction)))
                .thenReturn(this.transactionDTO);
        Assertions.assertNotNull(this.transactionsDao.getRandomTransaction());
    }

    @Test
    public void getRandomTransactionNoTransactionsTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(this.transactionBuilderImpl.buildDto(ArgumentMatchers.eq(this.transaction)))
                .thenReturn(this.transactionDTO);
        Assertions.assertThrows(NotFoundException.class,
                () -> this.transactionsDao.getRandomTransaction());
    }

    @Test
    public void getTransactionListTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(Arrays.asList(this.transaction, this.transaction));
        Mockito.when(this.transaction.getUserId()).thenReturn(123l);
        Mockito.when(this.transactionBuilderImpl.buildDto(ArgumentMatchers.eq(this.transaction)))
                .thenReturn(this.transactionDTO);
        List<TransactionDTO> transactionsList = this.transactionsDao.getTransactionList(123l);
        Assertions.assertNotNull(transactionsList);
        Assertions.assertEquals(2, transactionsList.size());
    }

    @Test
    public void getTransactionListNotMatchingTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(Arrays.asList(this.transaction, this.transaction));
        Mockito.when(this.transaction.getUserId()).thenReturn(123l);
        List<TransactionDTO> transactionsList = this.transactionsDao.getTransactionList(1234567l);
        Assertions.assertNotNull(transactionsList);
        Assertions.assertTrue(transactionsList.isEmpty());
    }

    @Test
    public void getTransactionListNoTransactionsTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(new ArrayList<>());
        List<TransactionDTO> transactionsList = this.transactionsDao.getTransactionList(1234567l);
        Assertions.assertNotNull(transactionsList);
        Assertions.assertTrue(transactionsList.isEmpty());
    }

    @Test
    public void getSumForUserTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(Arrays.asList(this.transaction, this.transaction));
        Mockito.when(this.transaction.getUserId()).thenReturn(123l);
        Mockito.when(this.transactionBuilderImpl.buildDto(ArgumentMatchers.eq(this.transaction)))
                .thenReturn(this.transactionDTO);
        Mockito.when(this.transactionDTO.getAmount())
                .thenReturn(5d)
                .thenReturn(45d);
        double sum = this.transactionsDao.getSumForUser(123l);
        Assertions.assertEquals(50d, sum);
    }

    @Test
    public void getSumForUserNotMatchingTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(Arrays.asList(this.transaction, this.transaction));
        Mockito.when(this.transaction.getUserId()).thenReturn(123l);
        double sum = this.transactionsDao.getSumForUser(1234567l);
        Assertions.assertEquals(0d, sum);
    }

    @Test
    public void getSumForUserNoTransactionsTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(new ArrayList<>());
        double sum = this.transactionsDao.getSumForUser(123l);
        Assertions.assertEquals(0d, sum);
    }

    @Test
    public void getTransactionReportTest() {
        TransactionDTO transaction = this.buildTransactionDTO("2020-06-11", 50d);
        TransactionDTO transaction2 = this.buildTransactionDTO("2020-06-12", 50d);
        TransactionDTO transaction3 = this.buildTransactionDTO("2020-06-03", 50d);
        TransactionDTO transaction4 = this.buildTransactionDTO("invalidDate", 50d);
        Mockito.when(this.transactionsRepository.findAll())
                .thenReturn(Arrays.asList(this.transaction, this.transaction, this.transaction, this.transaction));
        Mockito.when(this.transaction.getUserId())
                .thenReturn(123l);
        Mockito.when(this.transactionBuilderImpl.buildDto(ArgumentMatchers.eq(this.transaction)))
                .thenReturn(transaction)
                .thenReturn(transaction2)
                .thenReturn(transaction3)
                .thenReturn(transaction4);
        List<TransactionReport> transactionsList = this.transactionsDao.getTransactionReport(123l);

        Assertions.assertNotNull(transactionsList);
        Assertions.assertFalse(transactionsList.isEmpty());
        Assertions.assertEquals(3, transactionsList.size());
        TransactionReport report1 = transactionsList.get(0);
        Assertions.assertEquals("2020-06-01", report1.getWeekStartDate());
        Assertions.assertEquals("2020-06-04", report1.getWeekFinishDate());
        Assertions.assertEquals(0d, report1.getTotalAmount());
        Assertions.assertEquals(50d, report1.getAmount());
        TransactionReport report2 = transactionsList.get(1);
        Assertions.assertEquals("2020-06-05", report2.getWeekStartDate());
        Assertions.assertEquals("2020-06-11", report2.getWeekFinishDate());
        Assertions.assertEquals(50d, report2.getTotalAmount());
        Assertions.assertEquals(50d, report2.getAmount());
        TransactionReport report3 = transactionsList.get(2);
        Assertions.assertEquals("2020-06-12", report3.getWeekStartDate());
        Assertions.assertEquals("2020-06-18", report3.getWeekFinishDate());
        Assertions.assertEquals(100d, report3.getTotalAmount());
        Assertions.assertEquals(50d, report3.getAmount());
    }

    private TransactionDTO buildTransactionDTO(String transactionDate, double amount) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUserId(123l);
        transactionDTO.setAmount(amount);
        transactionDTO.setTransactionDate(transactionDate);
        return transactionDTO;
    }

    @Test
    public void getTransactionReportNotMatchingTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(Arrays.asList(this.transaction, this.transaction));
        Mockito.when(this.transaction.getUserId()).thenReturn(123l);
        List<TransactionReport> transactionsList = this.transactionsDao.getTransactionReport(1234567l);
        Assertions.assertNotNull(transactionsList);
        Assertions.assertTrue(transactionsList.isEmpty());
    }

    @Test
    public void getTransactionReportNoTransactionsTest() {
        Mockito.when(this.transactionsRepository.findAll()).thenReturn(new ArrayList<>());
        List<TransactionReport> transactionsList = this.transactionsDao.getTransactionReport(1234567l);
        Assertions.assertNotNull(transactionsList);
        Assertions.assertTrue(transactionsList.isEmpty());
    }

    private static Stream<Arguments> provideNotMatchingCases() {
        return Stream.of(
                Arguments.of("9a587cfe-70bd-4118", 123l),
                Arguments.of("9a587cfe-70bd-4118-a23c-6bbe30bd1eb0", 1l)
        );
    }

}
