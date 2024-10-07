package com.btgpactual.technicaltest.dto;

import com.btgpactual.technicaltest.validation.NotNullOrNotTextNull;

import lombok.Data;

@Data
public class CancelSuscriptionRequest {
	
	@NotNullOrNotTextNull(message = "userId is required and cannot be 'null'")
    private String userId;
	
	@NotNullOrNotTextNull(message = "fundId is required and cannot be 'null'")
    private String fundId;
    
};