package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.ECS.client.jax.Cart;
import com.ECS.client.jax.CartCreate;
import com.ECS.client.jax.CartCreateRequest;
import com.ECS.client.jax.OperationRequest;

import de.malkusch.amazon.ecs.ProductAdvertisingAPI;

public class CartCreateCall extends
		ApiCall<CartCreate, CartCreateRequest, Cart> {

	public CartCreateCall(ProductAdvertisingAPI api) {
		super(api, CartCreate.class);
	}

	@Override
	protected void call(CartCreate call,
			Holder<OperationRequest> operationRequest, Holder<List<Cart>> result) {

		api.getPort().cartCreate(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
