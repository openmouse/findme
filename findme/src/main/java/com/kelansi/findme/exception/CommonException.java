package com.kelansi.findme.exception;

public class CommonException extends RuntimeException {

	private static final long	serialVersionUID	= -6996598139140682680L;

	/**
	 * 
	 */
	public CommonException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public CommonException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CommonException(Throwable cause) {
		super(cause);
	}
}
