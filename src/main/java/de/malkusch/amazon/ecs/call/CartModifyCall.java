package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.ECS.client.jax.Cart;
import com.ECS.client.jax.CartModify;
import com.ECS.client.jax.CartModifyRequest;
import com.ECS.client.jax.OperationRequest;

import de.malkusch.amazon.ecs.ProductAdvertisingAPI;

public class CartModifyCall extends CartCall<CartModify, CartModifyRequest> {

	public CartModifyCall(ProductAdvertisingAPI api) {
		super(api, CartModify.class, CartModifyRequest.class);
	}

	@Override
	protected void call(CartModify call,
			Holder<OperationRequest> operationRequest, Holder<List<Cart>> result) {

		api.getPort().cartModify(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
