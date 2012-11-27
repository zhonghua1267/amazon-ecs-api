package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.ECS.client.jax.ItemSearch;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;
import com.ECS.client.jax.OperationRequest;

import de.malkusch.amazon.ecs.ProductAvertisingAPI;

public class ItemSeachCall extends ItemCall<ItemSearch, ItemSearchRequest> {

	public ItemSeachCall(ProductAvertisingAPI api) {
		super(api, ItemSearch.class);
	}

	@Override
	protected void call(ItemSearch call,
			Holder<OperationRequest> operationRequest,
			Holder<List<Items>> result) {

		api.getPort().itemSearch(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
