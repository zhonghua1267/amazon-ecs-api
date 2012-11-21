package de.malkusch.amazon.ecs.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import org.junit.Test;

import com.ECS.client.jax.AWSECommerceService;
import com.ECS.client.jax.AWSECommerceServicePortType;
import com.ECS.client.jax.ItemSearch;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;
import com.ECS.client.jax.OperationRequest;

import de.malkusch.amazon.ecs.SignatureHandler;
import de.malkusch.amazon.ecs.configuration.PropertiesConfiguration;

public class TestSignatureHandler {

	private PropertiesConfiguration configuration;

	public TestSignatureHandler() throws IOException
	{
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/amazon.properties"));
		configuration = new PropertiesConfiguration(properties);
	}

	@Test
	public void testAuthenticatedCommunication() throws UnsupportedEncodingException
	{
		AWSECommerceService service = new AWSECommerceService();

		AWSECommerceServicePortType port = service
				.getAWSECommerceServicePortDE();

		new SignatureHandler(configuration).appendHandler(((BindingProvider) port));

		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
		itemSearchRequest.setSearchIndex("Books");
		itemSearchRequest.setKeywords("Star Wars");
		itemSearchRequest.getResponseGroup().add("Large");

		ItemSearch itemSearch = new ItemSearch();
		itemSearch.setAWSAccessKeyId(configuration.getAccessKey());
		itemSearch.setAssociateTag(configuration.getAssociateTag());
		itemSearch.getRequest().add(itemSearchRequest);
		
		Holder<OperationRequest> operationRequest = null;
		Holder<List<Items>> items = new Holder<List<Items>>();
		port.itemSearch(itemSearch.getMarketplaceDomain(),
				itemSearch.getAWSAccessKeyId(), itemSearch.getAssociateTag(),
				itemSearch.getXMLEscaping(), itemSearch.getValidate(),
				itemSearch.getShared(), itemSearch.getRequest(),
				operationRequest, items);
		
		assertTrue(items.value.size() > 0);
	}

}
