package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Transactions;
import com.keepu.webAPI.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.keepu.webAPI.model.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
    List<Transactions> findByWallet(Wallet wallet);


    @Query("SELECT t FROM Transactions t WHERE t.wallet = :wallet "
            + "AND (:type IS NULL OR t.transactionType = :type) "
            + "AND (:startDate IS NULL OR t.transactionDate >= :startDate) "
            + "AND (:endDate IS NULL OR t.transactionDate <= :endDate) "
            + "AND (:minAmount IS NULL OR t.amount >= :minAmount) "
            + "AND (:maxAmount IS NULL OR t.amount <= :maxAmount)")

    List<Transactions> findFilteredTransactions(
            @Param("wallet") Wallet wallet,
            @Param("type") TransactionType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("minAmount") Double minAmount,
            @Param("maxAmount") Double maxAmount
    );

}
