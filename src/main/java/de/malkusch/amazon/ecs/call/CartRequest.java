package de.malkusch.amazon.ecs.call;

public interface CartRequest {

	public String setHMAC(String hmac);
	
	public String setCartId(String cartId);
	
}
