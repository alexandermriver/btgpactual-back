package com.btgpactual.technicaltest.exceptions;

import org.springframework.http.HttpStatus;

public class TransactionExceptions extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	
    public TransactionExceptions(HttpStatus status, String message) {
        super(message);
        this.status = status;
    };
	
    public HttpStatus getStatus() {
        return status;
    };
    
    public static class FundIdNotFoundExcepcion extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public FundIdNotFoundExcepcion(String message) {
            super(HttpStatus.NOT_FOUND, message);
        };
    };
    
    public static class UserNotFoundException extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public UserNotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        };
    };
    
    public static class UserIsSubscribedToFundException extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public UserIsSubscribedToFundException(String message) {
            super(HttpStatus.CONFLICT, message);
        };
    };
    
    public static class UserIsNotSubscribedToFundException extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public UserIsNotSubscribedToFundException(String message) {
            super(HttpStatus.CONFLICT, message);
        };
    };
    
    public static class MinimumAmoutException extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public MinimumAmoutException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        };
    };
    
    public static class InvalidEmailException extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public InvalidEmailException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        };
    };
    
    public static class InvalidPhoneNumberException extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public InvalidPhoneNumberException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        };
    };
    
    public static class ShortPhoneNumberException extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public ShortPhoneNumberException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        };
    };
    
    public static class AvailableBalanceExcepction extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public AvailableBalanceExcepction(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        };
    };
    
    public static class FundNotFoundException extends TransactionExceptions {
		private static final long serialVersionUID = 1L;
		public FundNotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        };
    };
    
};