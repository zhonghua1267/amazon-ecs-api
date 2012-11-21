package de.malkusch.amazon.ecs.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;

import de.malkusch.amazon.ecs.exception.RequestException;

public class TestAPI extends AbstractTest {

	
	public TestAPI() throws IOException {
		super();
	}
	
	@Test
	public void testItemSearch() throws RequestException
	{
		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
		itemSearchRequest.setSearchIndex("Books");
		itemSearchRequest.setKeywords("Star Wars");

		Items items = api.itemSearch(itemSearchRequest);
		
		assertTrue(items.getItem().size() > 0);
	}

	@Test(expected=RequestException.class)
	public void testFailItemSearch() throws RequestException
	{
		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();

		api.itemSearch(itemSearchRequest);
	}

}
