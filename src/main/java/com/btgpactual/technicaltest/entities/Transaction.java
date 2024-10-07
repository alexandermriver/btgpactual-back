package com.btgpactual.technicaltest.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "transactions")

public class Transaction {
	
    @Id
    private String transactionId;
    
    private String userId;
    private String fundId;
    private String fundName;
    private Double amount;
    private String transactionType; // subscription o cancellation
    private Date date;
    private String notificationMethod; // email o sms
    private String phoneNumber;
    private String email;

};