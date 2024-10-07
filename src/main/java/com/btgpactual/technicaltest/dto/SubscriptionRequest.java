package com.btgpactual.technicaltest.dto;

import com.btgpactual.technicaltest.enums.NotificationMethod;
import com.btgpactual.technicaltest.validation.NotNullOrNotTextNull;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionRequest {
	
	@NotNullOrNotTextNull(message = "userId is required and cannot be 'null'")
    private String userId;
	
	@NotNullOrNotTextNull(message = "fundId is required and cannot be 'null'")
    private String fundId;
	
    @NotNull(message = "amount")
    private Double amount;
	
    private NotificationMethod notificationMethod;
    
    @NotNullOrNotTextNull(message = "phoneNumber is required and cannot be 'null'")
    private String phoneNumber;
    
    @NotNullOrNotTextNull(message = "email is required and cannot be 'null'")
    private String email;
    
};