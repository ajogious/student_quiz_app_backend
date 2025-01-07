package com.ajogious.quiz_app_backend.exceptions;


public class QuestionNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuestionNotFoundException(String message) {
        super(message);
    }
}