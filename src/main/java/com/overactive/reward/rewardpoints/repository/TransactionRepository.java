package com.overactive.reward.rewardpoints.repository;

import com.overactive.reward.rewardpoints.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findTransactionById(Long id);
}
