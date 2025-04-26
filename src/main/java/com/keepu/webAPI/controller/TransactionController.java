package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.Transactions;
import com.keepu.webAPI.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transactions> getAll() {
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public Transactions getById(@PathVariable Integer id) {
        return transactionService.findById(id);
    }

    @PostMapping
    public Transactions save(@RequestBody Transactions transaction) {
        return transactionService.save(transaction);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        transactionService.deleteById(id);
    }
}
