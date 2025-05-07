package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
}