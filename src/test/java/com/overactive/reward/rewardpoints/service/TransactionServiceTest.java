package com.overactive.reward.rewardpoints.service;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.model.Transaction;
import com.overactive.reward.rewardpoints.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TransactionServiceTest {

    private static final Transaction TRANSACTION
            = Transaction
            .builder()
            .id(1L)
            .created(new Timestamp(new Date().getTime()))
            .value(120L)
            .build();

    private static final Transaction TRANSACTION2
            = Transaction
            .builder()
            .id(1L)
            .created(new Timestamp(new Date().getTime()))
            .value(20L)
            .build();

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void findAllTransactions() {
        transactionService.findAllTransactions();
    }

    @Test
    public void addTransaction() throws BusinessException {
        transactionService.addTransaction(TRANSACTION);
    }

    @Test(expected = Exception.class)
    public void addTransactionException() throws BusinessException {
        doThrow(DataAccessResourceFailureException.class).when(transactionRepository).save(any());
        transactionService.addTransaction(Transaction.builder().build());
    }

    @Test
    public void updateTransaction() throws BusinessException {
        when(transactionRepository.findTransactionById(TRANSACTION.getId())).thenReturn(Optional.of(TRANSACTION));
        transactionService.updateTransaction(TRANSACTION2);
    }

    @Test
    public void updateTransaction2() throws BusinessException {
        when(transactionRepository.findTransactionById(TRANSACTION.getId())).thenReturn(Optional.of(TRANSACTION));
        transactionService.updateTransaction(TRANSACTION);
    }

    @Test(expected = Exception.class)
    public void updateTransactionException() throws BusinessException {
        transactionService.updateTransaction(Transaction.builder().build());
    }
}