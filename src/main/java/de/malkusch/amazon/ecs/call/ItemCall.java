package de.malkusch.amazon.ecs.call;

import com.ECS.client.jax.Items;

import de.malkusch.amazon.ecs.ProductAdvertisingAPI;

public abstract class ItemCall<CallType, RequestType> extends
		ApiCall<CallType, RequestType, Items> {

	public ItemCall(ProductAdvertisingAPI api, Class<CallType> callClass) {
		super(api, callClass);
	}

}
