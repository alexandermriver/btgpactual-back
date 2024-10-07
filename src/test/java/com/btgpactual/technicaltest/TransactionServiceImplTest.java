package com.btgpactual.technicaltest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.btgpactual.technicaltest.dto.SubscriptionRequest;
import com.btgpactual.technicaltest.entities.Transaction;
import com.btgpactual.technicaltest.enums.NotificationMethod;
import com.btgpactual.technicaltest.repositories.TransactionRepository;
import com.btgpactual.technicaltest.services.IEmailService;
import com.btgpactual.technicaltest.services.impl.TransactionServiceImpl;
import com.btgpactual.technicaltest.exceptions.TransactionExceptions;

public class TransactionServiceImplTest {
    
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private IEmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    };

    @Test
    public void testSubscribeToFund_Success() {
    	
        SubscriptionRequest request = new SubscriptionRequest();
        request.setUserId("user123");
        request.setFundId("1");
        request.setAmount(75000.0);
        request.setPhoneNumber("1234567890");
        request.setEmail("alexandermriver@gmail.com");
        request.setNotificationMethod(NotificationMethod.EMAIL);

        when(transactionRepository.findTopByUserIdAndFundIdOrderByDateDesc("user123", "1")).thenReturn(null);
        when(transactionRepository.sumAmountByUserIdAndTransactionType("user123", "subscription")).thenReturn(0.0);
        
        Transaction result = transactionService.subscribeToFund(request);

        assertNotNull(result);
        assertEquals("user123", result.getUserId());
        assertEquals("1", result.getFundId());
        assertEquals("subscription", result.getTransactionType());
        verify(transactionRepository).save(any(Transaction.class));
        verify(emailService).sendEmail(eq("alexandermriver@gmail.com"), anyString(), anyString());
    };

    @Test
    public void testSubscribeToFund_UserAlreadySubscribed() {
      
        SubscriptionRequest request = new SubscriptionRequest();
        request.setUserId("user123");
        request.setFundId("1");
        request.setAmount(75000.0);
        request.setPhoneNumber("1234567890");
        request.setEmail("test@example.com");

        Transaction existingTransaction = new Transaction();
        existingTransaction.setTransactionType("subscription");

        when(transactionRepository.findTopByUserIdAndFundIdOrderByDateDesc("user123", "1")).thenReturn(existingTransaction);

        TransactionExceptions.UserIsSubscribedToFundException exception = assertThrows(
            TransactionExceptions.UserIsSubscribedToFundException.class,
            () -> transactionService.subscribeToFund(request)
        );

        assertEquals("El usuario ya está suscrito a este fondo.", exception.getMessage());
    };
};