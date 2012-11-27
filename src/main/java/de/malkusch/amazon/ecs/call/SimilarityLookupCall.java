package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.ECS.client.jax.Items;
import com.ECS.client.jax.OperationRequest;
import com.ECS.client.jax.SimilarityLookup;
import com.ECS.client.jax.SimilarityLookupRequest;

import de.malkusch.amazon.ecs.ProductAvertisingAPI;

public class SimilarityLookupCall extends
		ItemCall<SimilarityLookup, SimilarityLookupRequest> {

	public SimilarityLookupCall(ProductAvertisingAPI api) {
		super(api, SimilarityLookup.class);
	}

	@Override
	protected void call(SimilarityLookup call,
			Holder<OperationRequest> operationRequest,
			Holder<List<Items>> result) {

		api.getPort().similarityLookup(call.getMarketplaceDomain(),
				call.getAWSAccessKeyId(), call.getAssociateTag(),
				call.getXMLEscaping(), call.getValidate(), call.getShared(),
				call.getRequest(), operationRequest, result);
	}

}
