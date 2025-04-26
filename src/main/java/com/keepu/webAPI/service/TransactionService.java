package com.keepu.webAPI.service;

import com.keepu.webAPI.model.Transactions;
import com.keepu.webAPI.repository.TransactionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionsRepository transactionsRepository;

    public TransactionService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public List<Transactions> findAll() {
        return transactionsRepository.findAll();
    }

    public Transactions findById(Integer id) {
        return transactionsRepository.findById(id).orElse(null);
    }

    public Transactions save(Transactions transaction) {
        return transactionsRepository.save(transaction);
    }

    public void deleteById(Integer id) {
        transactionsRepository.deleteById(id);
    }
}
