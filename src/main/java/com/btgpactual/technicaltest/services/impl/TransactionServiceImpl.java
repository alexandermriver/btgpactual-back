package com.btgpactual.technicaltest.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.btgpactual.technicaltest.dto.CancelSuscriptionRequest;
import com.btgpactual.technicaltest.dto.SubscriptionRequest;
import com.btgpactual.technicaltest.entities.Transaction;
import com.btgpactual.technicaltest.enums.NotificationMethod;
import com.btgpactual.technicaltest.exceptions.TransactionExceptions;
import com.btgpactual.technicaltest.repositories.TransactionRepository;
import com.btgpactual.technicaltest.services.IEmailService;
import com.btgpactual.technicaltest.services.ITransactionService;

@Service
public class TransactionServiceImpl implements ITransactionService {
	
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired	
    private IEmailService emailService;
    
    private void validateIfUserIsAlreadySubscribedToFund(String userId, String fundId) {
	    Transaction lastTransaction = transactionRepository.findTopByUserIdAndFundIdOrderByDateDesc(userId, fundId);
	    if (lastTransaction != null && "subscription".equals(lastTransaction.getTransactionType())) {
	        throw new TransactionExceptions.UserIsSubscribedToFundException("El usuario ya está suscrito a este fondo.");
	    };
    };
    
    private Transaction validateIfUserIsNotSubscribedToFund(String userId, String fundId) {
		Transaction lastTransaction = transactionRepository.findTopByUserIdAndFundIdOrderByDateDesc(userId, fundId);
	    if (lastTransaction == null || !"subscription".equals(lastTransaction.getTransactionType())) {
	        throw new TransactionExceptions.UserIsNotSubscribedToFundException("El usuario no está suscrito a este fondo.");
	    };
	    return lastTransaction;
    };
    
    private void validateIfTheMinimumAmountiIsMet(String fundId, Double amount) {
        if (amount < getMinimumAmountForFund(fundId)) {
            throw new TransactionExceptions.MinimumAmoutException("El monto es inferior al mínimo requerido para este fondo.");
        };
    };
    
    private void validateAvailableBalance(String userId, String fundId, Double amount) {
    	double totalCommittedAmount = getTotalCommittedAmount(userId, amount);
        if (totalCommittedAmount + amount > 500000) {
            throw new TransactionExceptions.AvailableBalanceExcepction("No tiene saldo disponible para vincularse al fondo " + getFundName(fundId) + ".");
        };
    };
    
    private void validateIfUserExists(String userId) {
    	if (!transactionRepository.existsByUserId(userId)) {
    		throw new TransactionExceptions.UserNotFoundException("Usuario " + userId + " no existe.");
    	};
    };
    
    private void validateIfFundExists(String fundId) {
    	if (getMinimumAmountForFund(fundId) == 0) {
    		throw new TransactionExceptions.FundIdNotFoundExcepcion("Fondo " + fundId + " no existe.");
    	};
    };
    
    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d+")) {
            throw new TransactionExceptions.InvalidPhoneNumberException("El número de celular solo puede contener dígitos.");
        };
        if (phoneNumber.length() != 10) {
            throw new TransactionExceptions.ShortPhoneNumberException("El número de celular debe tener exactamente 10 dígitos.");
        };
    };

    private void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || !email.matches(emailRegex)) {
            throw new TransactionExceptions.InvalidEmailException("El correo electrónico no tiene un formato válido.");
        };
    };

	@Override
    public Transaction subscribeToFund(SubscriptionRequest request) {

		validateIfUserIsAlreadySubscribedToFund(request.getUserId(), request.getFundId());
		validateIfTheMinimumAmountiIsMet(request.getFundId(), request.getAmount());
		validateAvailableBalance(request.getUserId(), request.getFundId(), request.getAmount()); 
		validateIfFundExists(request.getFundId());
		validatePhoneNumber(request.getPhoneNumber());
		validateEmail(request.getEmail());
        
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setUserId(request.getUserId());
        transaction.setFundId(request.getFundId());
        transaction.setFundName(getFundName(request.getFundId()));
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("subscription");
        transaction.setDate(new Date());
        transaction.setNotificationMethod(request.getNotificationMethod().toString());
        transaction.setPhoneNumber(request.getPhoneNumber().toString());
        transaction.setEmail(request.getEmail());
        
        transactionRepository.save(transaction);
        
        if (NotificationMethod.EMAIL.equals(request.getNotificationMethod())) {
        	String msg = "Has realizado una suscripción exitosa al fondo " + getFundName(request.getFundId()) + " con un monto de " + request.getAmount();
        	emailService.sendEmail(request.getEmail(), "Subscripción Exitosa", msg);
        } else {
        	//LÓGICA PARA ENVÍO DE MENSAJE DE TEXTO
        };

        return transaction;
    };

	@Override
    public List<Transaction> getUserTransactions(String userId) {
		validateIfUserExists(userId);
        return transactionRepository.findByUserId(userId);
    };
    
	@Override
	public Transaction cancelFundSubscription(CancelSuscriptionRequest request) {
		
		validateIfUserExists(request.getUserId());
		validateIfFundExists(request.getFundId());
	    
	    Transaction lastTransaction = validateIfUserIsNotSubscribedToFund(request.getUserId(), request.getFundId());

        Transaction cancellation = new Transaction();
        cancellation.setTransactionId(UUID.randomUUID().toString());
        cancellation.setUserId(request.getUserId());
        cancellation.setFundId(request.getFundId());
        cancellation.setFundName(lastTransaction.getFundName());
        cancellation.setAmount(lastTransaction.getAmount());
        cancellation.setTransactionType("cancelSubscription");
        cancellation.setNotificationMethod(lastTransaction.getNotificationMethod());
        cancellation.setDate(new Date());
        cancellation.setPhoneNumber(cancellation.getPhoneNumber());
        cancellation.setEmail(cancellation.getEmail());
        
        return transactionRepository.save(cancellation);
	};
    
    //SE SIMULA 
    private double getMinimumAmountForFund(String fundId) {
        switch (fundId) {
            case "1": return 75000;
            case "2": return 125000;
            case "3": return 50000;
            case "4": return 250000;
            case "5": return 100000;
            default: return 0;
        }
    };
    
    private String getFundName(String fundId) {
        switch (fundId) {
            case "1": return "FPV_BTG_PACTUAL_RECAUDADORA";
            case "2": return "FPV_BTG_PACTUAL_ECOPETROL";
            case "3": return "DEUDAPRIVADA";
            case "4": return "FDO-ACCIONES";
            case "5": return "FPV_BTG_PACTUAL_DINAMICA";
            default: return null;
        }
    };
    
    public double getTotalCommittedAmount(String userId, Double amount) {
        Double totalSubscriptions = transactionRepository.sumAmountByUserIdAndTransactionType(userId, "subscription");
        if (totalSubscriptions == null) {
            totalSubscriptions = 0.0;
        };
 
        Double totalCancellations = transactionRepository.sumAmountByUserIdAndTransactionType(userId, "cancelSubscription");
        if (totalCancellations == null) {
            totalCancellations = 0.0;
        };
        
        double totalCommittedAmount = totalSubscriptions - totalCancellations;
        return totalCommittedAmount;
    };

};