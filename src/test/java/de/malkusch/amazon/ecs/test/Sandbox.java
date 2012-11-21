package de.malkusch.amazon.ecs.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.ECS.client.jax.Item;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;

import de.malkusch.amazon.ecs.exception.RequestException;

public class Sandbox extends AbstractTest {


	public Sandbox() throws IOException {
		super();
	}

	@Test
	public void test() throws UnsupportedEncodingException, RequestException
	{
		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
		itemSearchRequest.setSearchIndex("Books");
		itemSearchRequest.setKeywords("Star Wars");

		Items items = api.itemSearch(itemSearchRequest);
		
		for (Item item : items.getItem()) {
			System.out.println(item.getItemAttributes().getTitle());

		}
	}
	
}