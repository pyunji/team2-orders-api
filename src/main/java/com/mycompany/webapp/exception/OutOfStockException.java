package com.mycompany.webapp.exception;

public class OutOfStockException extends RuntimeException{
	public OutOfStockException() {
		super("품절");
	}
	
	public OutOfStockException(String message) {
		super(message);
	}
}
