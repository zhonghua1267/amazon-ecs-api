package de.malkusch.amazon.ecs.test;

import java.io.IOException;

import org.junit.Test;

import com.ECS.client.jax.ItemSearchRequest;

import de.malkusch.amazon.ecs.exception.RequestException;

public class TestAPI extends AbstractTest {

	
	public TestAPI() throws IOException {
		super();
	}

	@Test(expected=RequestException.class)
	public void testFailItemSearch() throws RequestException
	{
		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();

		api.itemSearch(itemSearchRequest);
	}

}
