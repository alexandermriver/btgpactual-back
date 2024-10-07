package com.btgpactual.technicaltest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.btgpactual.technicaltest.dto.CancelSuscriptionRequest;
import com.btgpactual.technicaltest.dto.SubscriptionRequest;
import com.btgpactual.technicaltest.entities.Transaction;
import com.btgpactual.technicaltest.services.ITransactionService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/subscribe")
    public ResponseEntity<Transaction> subscribeToFund(@Valid @RequestBody SubscriptionRequest request) {
        Transaction transaction = transactionService.subscribeToFund(request);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    };

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable String userId) {
        List<Transaction> transactions = transactionService.getUserTransactions(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    };
    
    @PostMapping("/cancelSubscription")
    public ResponseEntity<Transaction> cancelFundSubscription(@Valid @RequestBody CancelSuscriptionRequest request) {
        Transaction transactions = transactionService.cancelFundSubscription(request);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    };
    
};