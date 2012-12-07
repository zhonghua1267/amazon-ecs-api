package de.malkusch.amazon.ecs.exception;


public class RequestException extends ProductAdvertisingAPIException {

	private static final long serialVersionUID = -271838209562081506L;
	
	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestException(Throwable cause) {
		super(cause);
	}

	public RequestException(String message) {
		super(message);
	}

	public RequestException() {
	}
	
	

}
