package de.malkusch.amazon.ecs.exception;

import com.ECS.client.jax.Errors.Error;

public class RequestErrorException extends RequestException {
	
	private static final long serialVersionUID = 790813551184073638L;
	
	private Error error;

	public RequestErrorException(Error error) {
		this(error, null);
	}

	public RequestErrorException(Error error, Throwable cause) {
		super(error.getMessage(), cause);
		
		this.error = error;
	}
	
	public String getCode() {
		return error.getCode();
	}
	
}
