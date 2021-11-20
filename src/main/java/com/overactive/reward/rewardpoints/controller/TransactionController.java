package com.overactive.reward.rewardpoints.controller;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.model.Transaction;
import com.overactive.reward.rewardpoints.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction (
            @RequestBody Transaction transaction){
        try{
            Transaction transaction1 = transactionService.addTransaction(transaction);
            return new ResponseEntity<>(transaction1, HttpStatus.CREATED);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Transaction> updateTransaction (
            @RequestBody Transaction transaction){
        try{
            Transaction transaction1 = transactionService.updateTransaction(transaction);
            return new ResponseEntity<>(transaction1, HttpStatus.OK);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
