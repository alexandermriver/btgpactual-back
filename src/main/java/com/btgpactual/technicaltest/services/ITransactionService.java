package com.btgpactual.technicaltest.services;

import com.btgpactual.technicaltest.dto.CancelSuscriptionRequest;
import com.btgpactual.technicaltest.dto.SubscriptionRequest;
import com.btgpactual.technicaltest.entities.Transaction;

import java.util.List;

public interface ITransactionService {

    public Transaction subscribeToFund(SubscriptionRequest request);

    public List<Transaction> getUserTransactions(String userId);
    
    public Transaction cancelFundSubscription(CancelSuscriptionRequest request);
    
};