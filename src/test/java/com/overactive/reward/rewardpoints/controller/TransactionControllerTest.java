package com.overactive.reward.rewardpoints.controller;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.model.Transaction;
import com.overactive.reward.rewardpoints.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TransactionControllerTest {

    private static final Transaction TRANSACTION
            = Transaction
            .builder()
            .id(1L)
            .created(new Timestamp(new Date().getTime()))
            .value(120L)
            .build();

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Test
    public void getAllTransactions() {
        ResponseEntity<List<Transaction>> responseEntity = transactionController.getAllTransactions();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void addTransaction() {
        ResponseEntity<Transaction> responseEntity = transactionController.addTransaction(TRANSACTION);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void addTransactionException() throws BusinessException {
        doThrow(BusinessException.class).when(transactionService)
                .addTransaction(any());
        ResponseEntity<Transaction> responseEntity = transactionController.addTransaction(Transaction.builder().build());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void updateTransaction() {
        ResponseEntity<Transaction> responseEntity = transactionController.updateTransaction(TRANSACTION);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void updateTransactionException() throws BusinessException {
        doThrow(BusinessException.class).when(transactionService)
                .updateTransaction(any());
        ResponseEntity<Transaction> responseEntity = transactionController.updateTransaction(Transaction.builder().build());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}