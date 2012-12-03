package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.ECS.client.jax.Cart;
import com.ECS.client.jax.CartGet;
import com.ECS.client.jax.CartGetRequest;
import com.ECS.client.jax.OperationRequest;

import de.malkusch.amazon.ecs.ProductAdvertisingAPI;
import de.malkusch.amazon.ecs.exception.RequestException;

public class CartGetCall extends CartCall<CartGet, CartGetRequest> {

	public CartGetCall(ProductAdvertisingAPI api) {
		super(api, CartGet.class, CartGetRequest.class);
	}

	@Override
	protected void call(CartGet call,
			Holder<OperationRequest> operationRequest, Holder<List<Cart>> result) {

		api.getPort().cartGet(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

	public Cart call(Cart cart) throws RequestException {
		return call(buildRequest(cart));
	}

}
