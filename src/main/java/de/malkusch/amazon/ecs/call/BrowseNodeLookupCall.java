package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.ECS.client.jax.BrowseNodeLookup;
import com.ECS.client.jax.BrowseNodeLookupRequest;
import com.ECS.client.jax.BrowseNodes;
import com.ECS.client.jax.OperationRequest;

import de.malkusch.amazon.ecs.ProductAvertisingAPI;

public class BrowseNodeLookupCall extends
		ApiCall<BrowseNodeLookup, BrowseNodeLookupRequest, BrowseNodes> {

	public BrowseNodeLookupCall(ProductAvertisingAPI api) {
		super(api, BrowseNodeLookup.class);
	}

	@Override
	protected void call(BrowseNodeLookup call,
			Holder<OperationRequest> operationRequest,
			Holder<List<BrowseNodes>> result) {

		api.getPort().browseNodeLookup(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
