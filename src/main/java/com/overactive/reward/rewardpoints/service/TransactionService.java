package com.overactive.reward.rewardpoints.service;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.exception.TransactionNotFoundException;
import com.overactive.reward.rewardpoints.model.Transaction;
import com.overactive.reward.rewardpoints.repository.TransactionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findAllTransactions() { return transactionRepository.findAll(); }

    public Transaction addTransaction(Transaction transaction) throws BusinessException {
        try {
            log.info("Saving user..");
            return transactionRepository.save(transaction);
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Transaction updateTransaction(Transaction transaction) throws BusinessException {
        try {
            Transaction transaction1 = transactionRepository.findTransactionById(transaction.getId())
                    .orElseThrow(() -> new TransactionNotFoundException("User with id " + transaction.getId() + " not found"));
            if (!transaction1.equals(transaction)) {
                log.info("Updating transaction with id " + transaction.getId());
                return transactionRepository.save(transaction);
            }
            log.info("transaction was not updated");
            return transaction1;
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
