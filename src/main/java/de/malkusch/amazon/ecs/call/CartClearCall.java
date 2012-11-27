package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.ECS.client.jax.Cart;
import com.ECS.client.jax.CartClear;
import com.ECS.client.jax.CartClearRequest;
import com.ECS.client.jax.OperationRequest;

import de.malkusch.amazon.ecs.ProductAvertisingAPI;
import de.malkusch.amazon.ecs.exception.RequestException;

public class CartClearCall extends CartCall<CartClear, CartClearRequest> {

	public CartClearCall(ProductAvertisingAPI api) {
		super(api, CartClear.class, CartClearRequest.class);
	}

	@Override
	protected void call(CartClear call,
			Holder<OperationRequest> operationRequest, Holder<List<Cart>> result) {

		api.getPort().cartClear(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

	public Cart call(Cart cart) throws RequestException {
		return call(buildRequest(cart));
	}

}
