package de.malkusch.amazon.ecs.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.junit.Test;

import com.ECS.client.jax.AWSECommerceService;
import com.ECS.client.jax.Item;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;

import de.malkusch.amazon.ecs.ProductAvertisingAPI;
import de.malkusch.amazon.ecs.configuration.PropertiesConfiguration;

public class Sandbox {

	private PropertiesConfiguration configuration;

	public Sandbox() throws IOException
	{
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/amazon.properties"));
		configuration = new PropertiesConfiguration(properties);
	}

	@Test
	public void test() throws UnsupportedEncodingException
	{
		ProductAvertisingAPI api = new ProductAvertisingAPI(configuration, new AWSECommerceService().getAWSECommerceServicePortDE());

		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
		itemSearchRequest.setSearchIndex("Books");
		itemSearchRequest.setKeywords("Star Wars");

		Items items = api.itemSearch(itemSearchRequest);
		
		for (Item item : items.getItem()) {
			System.out.println(item.getItemAttributes().getTitle());

		}
	}
	
}