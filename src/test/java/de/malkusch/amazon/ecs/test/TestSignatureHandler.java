package de.malkusch.amazon.ecs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.junit.Test;

import com.ECS.client.jax.AWSECommerceService;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;

import de.malkusch.amazon.ecs.ProductAvertisingAPI;
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
		ProductAvertisingAPI api = new ProductAvertisingAPI(configuration, new AWSECommerceService().getAWSECommerceServicePortDE());

		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
		itemSearchRequest.setKeywords("Star Wars");

		Items items = api.itemSearch(itemSearchRequest);
		
		assertEquals("True", items.getRequest().getIsValid());
	}

}
