package de.malkusch.amazon.ecs.call;

import com.ECS.client.jax.Items;

import de.malkusch.amazon.ecs.ProductAvertisingAPI;

public abstract class ItemCall<CallType, RequestType> extends ApiCall<CallType, RequestType, Items> {

	public ItemCall(ProductAvertisingAPI api, Class<CallType> callClass) {
		super(api, callClass);
	}

}
