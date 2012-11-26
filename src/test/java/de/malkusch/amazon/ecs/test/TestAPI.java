package de.malkusch.amazon.ecs.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.ECS.client.jax.ItemLookupRequest;
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
	
	@Test
	public void testItemLookup() throws RequestException
	{
		ItemLookupRequest request = new ItemLookupRequest();
		request.getItemId().add("383102037X");
		
		Items items = api.itemLookup(request);
		
		assertTrue(items.getItem().size() == 1);
	}
	
	@Test(expected=RequestException.class)
	public void testFailItemLookup() throws RequestException
	{
		ItemLookupRequest request = new ItemLookupRequest();
		
		api.itemLookup(request);
	}

}
