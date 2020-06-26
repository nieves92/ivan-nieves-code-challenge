package com.clip.challenge.controller;

import com.clip.challenge.dao.TransactionsDao;
import com.clip.challenge.dto.TransactionDTO;
import com.clip.challenge.model.TransactionReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;

import static com.clip.challenge.constants.AppConstants.SUM_HEADER;
import static com.clip.challenge.constants.AppConstants.USER_ID_HEADER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RestController
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RequestMapping("/clip/v1/transactions")
public class ServiceController {

    @Autowired
    protected TransactionsDao transactionsDao;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public TransactionDTO addTransaction(@RequestParam("userId") long userId, @Valid @RequestBody TransactionDTO transactionDTO) {
        transactionDTO.setUserId(userId);
        return this.transactionsDao.addTransaction(transactionDTO);
    }

    @GetMapping()
    public List<TransactionDTO> listTransactions(@RequestParam("userId") long userId) {
        return this.transactionsDao.getTransactionList(userId);
    }

    @GetMapping("/report")
    public List<TransactionReport> getTransactionsReport(@RequestParam("userId") long userId) {
        return this.transactionsDao.getTransactionReport(userId);
    }

    @GetMapping("/random")
    public TransactionDTO getRandomTransaction() {
        return this.transactionsDao.getRandomTransaction();
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO showTransaction(@PathVariable("transactionId") String transactionId, @RequestParam("userId") long userId) {
        return this.transactionsDao.getTransaction(transactionId, userId);
    }

    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Object> sumTransactions(@RequestParam("userId") long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(USER_ID_HEADER, String.valueOf(userId));
        headers.set(SUM_HEADER, String.valueOf(this.transactionsDao.getSumForUser(userId)));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
