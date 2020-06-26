package com.clip.challenge.controller;

import com.clip.challenge.Main;
import com.clip.challenge.dao.TransactionsDao;
import com.clip.challenge.dto.TransactionDTO;
import com.clip.challenge.model.TransactionReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;

import static com.clip.challenge.constants.AppConstants.SUM_HEADER;
import static com.clip.challenge.constants.AppConstants.USER_ID_HEADER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class ServiceControllerIntegrationTest implements IntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TransactionsDao transactionsDao;

    private static final String BASE_PATH = "/clip/v1/transactions";
    private static final String DATE = "2020-10-10";
    private static final String DESCRIPTION = "Transaction test";
    private static final String TRANSACTION_ID = "9a587cfe-70bd-4118-a23c-6bbe30bd1eb0";
    private static final double AMOUNT = 200.0;
    private static final double TOTAL_AMOUNT = 600.0;
    private static final int QUANTITY = 4;
    private static final long USER_ID = 123l;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTransactionTest() throws Exception {
        TransactionDTO transactionDTO = this.buildTransactionDTO();
        Mockito.when(this.transactionsDao.addTransaction(ArgumentMatchers.any(TransactionDTO.class)))
                .thenReturn(transactionDTO);
        mvc.perform(post(BASE_PATH + "?userId=" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.readJsonFile("add-transaction.json")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", is((int) USER_ID)))
                .andExpect(jsonPath("$.transactionId", is(TRANSACTION_ID)))
                .andExpect(jsonPath("$.date", is(DATE)))
                .andExpect(jsonPath("$.amount", is(AMOUNT)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));
    }

    @Test
    void addTransactionInvalidDateTest() throws Exception {
        TransactionDTO transactionDTO = this.buildTransactionDTO();
        Mockito.when(this.transactionsDao.addTransaction(ArgumentMatchers.any(TransactionDTO.class)))
                .thenReturn(transactionDTO);
        mvc.perform(post(BASE_PATH + "?userId=" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.readJsonFile("add-transaction-invalid-date.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addTransactionFutureDateTest() throws Exception {
        TransactionDTO transactionDTO = this.buildTransactionDTO();
        Mockito.when(this.transactionsDao.addTransaction(ArgumentMatchers.any(TransactionDTO.class)))
                .thenReturn(transactionDTO);
        mvc.perform(post(BASE_PATH + "?userId=" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.readJsonFile("add-transaction-future-date.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listTransactionsTest() throws Exception {
        TransactionDTO transactionDTO = this.buildTransactionDTO();
        Mockito.when(this.transactionsDao.getTransactionList(USER_ID))
                .thenReturn(Arrays.asList(transactionDTO));
        mvc.perform(get(BASE_PATH + "?userId=" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is((int) USER_ID)))
                .andExpect(jsonPath("$[0].transactionId", is(TRANSACTION_ID)))
                .andExpect(jsonPath("$[0].date", is(DATE)))
                .andExpect(jsonPath("$[0].amount", is(AMOUNT)))
                .andExpect(jsonPath("$[0].description", is(DESCRIPTION)));
    }

    @Test
    void getTransactionsReportTest() throws Exception {
        TransactionReport transactionReport = new TransactionReport();
        transactionReport.setUserId(USER_ID);
        transactionReport.setTotalAmount(TOTAL_AMOUNT);
        transactionReport.setAmount(AMOUNT);
        transactionReport.setQuantity(QUANTITY);
        transactionReport.setWeekStartDate(DATE);
        transactionReport.setWeekFinishDate(DATE);
        Mockito.when(this.transactionsDao.getTransactionReport(USER_ID))
                .thenReturn(Arrays.asList(transactionReport));
        mvc.perform(get(BASE_PATH + "/report?userId=" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is((int) USER_ID)))
                .andExpect(jsonPath("$[0].totalAmount", is(TOTAL_AMOUNT)))
                .andExpect(jsonPath("$[0].quantity", is(QUANTITY)))
                .andExpect(jsonPath("$[0].amount", is(AMOUNT)))
                .andExpect(jsonPath("$[0].weekStartDate", is(DATE)))
                .andExpect(jsonPath("$[0].weekFinishDate", is(DATE)));
    }

    @Test
    void getRandomTransactionTest() throws Exception {
        TransactionDTO transactionDTO = this.buildTransactionDTO();
        Mockito.when(this.transactionsDao.getRandomTransaction()).thenReturn(transactionDTO);
        mvc.perform(get(BASE_PATH + "/random")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is((int) USER_ID)))
                .andExpect(jsonPath("$.transactionId", is(TRANSACTION_ID)))
                .andExpect(jsonPath("$.date", is(DATE)))
                .andExpect(jsonPath("$.amount", is(AMOUNT)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));
    }

    @Test
    void showTransactionTest() throws Exception {
        TransactionDTO transactionDTO = this.buildTransactionDTO();
        Mockito.when(this.transactionsDao.getTransaction(TRANSACTION_ID, USER_ID)).thenReturn(transactionDTO);
        mvc.perform(get(BASE_PATH + "/" + TRANSACTION_ID + "?userId=" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is((int) USER_ID)))
                .andExpect(jsonPath("$.transactionId", is(TRANSACTION_ID)))
                .andExpect(jsonPath("$.date", is(DATE)))
                .andExpect(jsonPath("$.amount", is(AMOUNT)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));
    }

    @Test
    void sumTransactionsTest() throws Exception {
        double expectedSum = 345678.0d;
        Mockito.when(this.transactionsDao.getSumForUser(USER_ID)).thenReturn(expectedSum);
        mvc.perform(head(BASE_PATH + "?userId=" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(USER_ID_HEADER, String.valueOf(USER_ID)))
                .andExpect(header().string(SUM_HEADER, String.valueOf(expectedSum)));
    }

    private TransactionDTO buildTransactionDTO() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionDate(DATE);
        transactionDTO.setAmount(AMOUNT);
        transactionDTO.setUserId(USER_ID);
        transactionDTO.setDescription(DESCRIPTION);
        transactionDTO.setTransactionId(TRANSACTION_ID);
        return transactionDTO;
    }
}