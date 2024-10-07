package com.btgpactual.technicaltest.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.btgpactual.technicaltest.entities.Transaction;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
	
    List<Transaction> findByUserId(String userId);

    boolean existsByUserIdAndFundIdAndTransactionType(String userId, String fundId, String transactionType);

    Transaction findTopByUserIdAndFundIdOrderByDateDesc(String userId, String fundId);

    boolean existsByUserId(String userId);
    
    @Aggregation(pipeline = {
    	    "{ '$match': { 'userId': ?0, 'transactionType': ?1 } }",
    	    "{ '$group': { '_id': null, 'totalAmount': { '$sum': '$amount' } } }"
    })
    Double sumAmountByUserIdAndTransactionType(String userId, String transactionType);

    
};