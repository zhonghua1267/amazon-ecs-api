package de.malkusch.amazon.ecs.exception;

public class ProductAdvertisingAPIException extends Exception {

	private static final long serialVersionUID = 2445956591143092401L;
	
	public ProductAdvertisingAPIException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductAdvertisingAPIException(Throwable cause) {
		super(cause);
	}

	public ProductAdvertisingAPIException(String message) {
		super(message);
	}

	public ProductAdvertisingAPIException() {
	}

}
