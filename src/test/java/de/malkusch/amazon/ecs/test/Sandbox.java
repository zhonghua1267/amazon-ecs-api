package de.malkusch.amazon.ecs.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.handler.Handler;

import org.junit.Test;

import com.ECS.client.jax.AWSECommerceService;
import com.ECS.client.jax.AWSECommerceServicePortType;
import com.ECS.client.jax.Item;
import com.ECS.client.jax.ItemSearch;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;
import com.ECS.client.jax.OperationRequest;

import de.malkusch.amazon.ecs.SignatureHandler;

public class Sandbox {

	String accessKey;
	String secretKey;
	String associateTag;

	public Sandbox() throws IOException {
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/amazon.properties"));

		accessKey = properties.getProperty("accessKey");
		secretKey = properties.getProperty("secretKey");
		associateTag = properties.getProperty("associateTag");
	}

	@Test
	public void test() throws UnsupportedEncodingException {
		AWSECommerceService service = new AWSECommerceService();

		AWSECommerceServicePortType port = service
				.getAWSECommerceServicePortDE();

		// service.setHandlerResolver(new AwsHandlerResolver(secretKey));
		Binding binding = ((BindingProvider) port).getBinding();
		List<Handler> handlerList = binding.getHandlerChain();
		handlerList.add(new SignatureHandler(accessKey, secretKey));
		binding.setHandlerChain(handlerList);

		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
		itemSearchRequest.setSearchIndex("Books");
		itemSearchRequest.setKeywords("Star Wars");
		itemSearchRequest.getResponseGroup().add("Large");

		ItemSearch itemSearch = new ItemSearch();
		itemSearch.setAWSAccessKeyId(accessKey);
		itemSearch.setAssociateTag(associateTag);
		itemSearch.getRequest().add(itemSearchRequest);

		Holder<OperationRequest> operationRequest = null;
		Holder<List<Items>> items = new Holder<List<Items>>();
		port.itemSearch(itemSearch.getMarketplaceDomain(),
				itemSearch.getAWSAccessKeyId(), itemSearch.getAssociateTag(),
				itemSearch.getXMLEscaping(), itemSearch.getValidate(),
				itemSearch.getShared(), itemSearch.getRequest(),
				operationRequest, items);

		Items retval = items.value.get(0); // first and only Items element
		List<Item> item_list = retval.getItem(); // list of Item subelements
		for (Item item : item_list) {
			System.out.println(item.getItemAttributes().getTitle());

		}
	}

}
