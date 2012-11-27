package de.malkusch.amazon.ecs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.Test;

import com.ECS.client.jax.BrowseNodeLookupRequest;
import com.ECS.client.jax.BrowseNodes;
import com.ECS.client.jax.Cart;
import com.ECS.client.jax.CartAddRequest;
import com.ECS.client.jax.CartClearRequest;
import com.ECS.client.jax.CartCreateRequest;
import com.ECS.client.jax.CartCreateRequest.Items.Item;
import com.ECS.client.jax.CartGetRequest;
import com.ECS.client.jax.CartModifyRequest;
import com.ECS.client.jax.ItemLookupRequest;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;
import com.ECS.client.jax.SimilarityLookupRequest;

import de.malkusch.amazon.ecs.exception.RequestException;

public class TestAPI extends AbstractTest {

	public TestAPI() throws IOException {
		super();
	}

	@Test
	public void testItemSearch() throws RequestException {
		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
		itemSearchRequest.setSearchIndex("Books");
		itemSearchRequest.setKeywords("Star Wars");

		Items items = api.getItemSearch().call(itemSearchRequest);

		assertTrue(items.getItem().size() > 0);
	}

	@Test(expected = RequestException.class)
	public void testFailItemSearch() throws RequestException {
		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();

		api.getItemSearch().call(itemSearchRequest);
	}

	@Test
	public void testItemLookup() throws RequestException {
		ItemLookupRequest request = new ItemLookupRequest();
		request.getItemId().add("383102037X");

		Items items = api.getItemLookup().call(request);

		assertTrue(items.getItem().size() == 1);
	}

	@Test(expected = RequestException.class)
	public void testFailItemLookup() throws RequestException {
		ItemLookupRequest request = new ItemLookupRequest();

		api.getItemLookup().call(request);
	}

	@Test(expected = RequestException.class)
	public void testFailBrowseNodeLookup() throws RequestException {
		BrowseNodeLookupRequest request = new BrowseNodeLookupRequest();

		api.getBrowseNodeLookup().call(request);
	}

	@Test
	public void testBrowseNodeLookup() throws RequestException {
		BrowseNodeLookupRequest request = new BrowseNodeLookupRequest();
		request.getBrowseNodeId().add("78689031");

		BrowseNodes nodes = api.getBrowseNodeLookup().call(request);

		assertTrue(nodes.getBrowseNode().size() == 1);
		assertEquals(request.getBrowseNodeId().get(0), nodes.getBrowseNode()
				.get(0).getBrowseNodeId());
	}

	@Test(expected = RequestException.class)
	public void testFailCartCreate() throws RequestException {
		CartCreateRequest request = new CartCreateRequest();

		api.getCartCreate().call(request);
	}

	@Test
	public void testCartCreate() throws RequestException {
		CartCreateRequest request = new CartCreateRequest();

		CartCreateRequest.Items items = new CartCreateRequest.Items();
		request.setItems(items);

		Item item = new Item();
		items.getItem().add(item);

		item.setASIN("383102037X");
		item.setQuantity(BigInteger.valueOf(1));

		Cart cart = api.getCartCreate().call(request);

		assertNotNull(cart.getPurchaseURL());
	}

	@Test(expected = RequestException.class)
	public void testFailCartGet() throws RequestException {
		CartGetRequest request = new CartGetRequest();

		api.cartGet(request);
	}

	@Test
	public void testCartGet() throws RequestException {
		Cart createdCart = null;
		{
			CartCreateRequest request = new CartCreateRequest();

			CartCreateRequest.Items items = new CartCreateRequest.Items();
			request.setItems(items);

			Item item = new Item();
			items.getItem().add(item);

			item.setASIN("383102037X");
			item.setQuantity(BigInteger.valueOf(1));

			createdCart = api.getCartCreate().call(request);
		}

		Cart cart = api.cartGet(createdCart);

		assertEquals(createdCart.getCartId(), cart.getCartId());
		assertTrue(cart.getCartItems().getCartItem().size() == 1);
		assertEquals(createdCart.getCartItems().getCartItem().get(0).getASIN(),
				cart.getCartItems().getCartItem().get(0).getASIN());
	}

	@Test(expected = RequestException.class)
	public void testFailCartModify() throws RequestException {
		CartModifyRequest request = new CartModifyRequest();

		api.cartModify(request);
	}

	@Test
	public void testCartModify() throws RequestException {
		Cart createdCart = null;
		{
			CartCreateRequest request = new CartCreateRequest();

			CartCreateRequest.Items items = new CartCreateRequest.Items();
			request.setItems(items);

			String[] asins = new String[] { "383102037X", "3831019592",
					"3831091005", "3442380456", "3831020612", "3831018324",
					"3833222476" };
			for (String asin : asins) {
				Item item = new Item();
				item.setASIN(asin);
				item.setQuantity(BigInteger.valueOf(3));
				items.getItem().add(item);

			}

			createdCart = api.getCartCreate().call(request);
		}

		CartModifyRequest request = api.buildCartModifyRequest(createdCart);
		request.setItems(new CartModifyRequest.Items());

		// decrease
		CartModifyRequest.Items.Item decreasedItem = new CartModifyRequest.Items.Item();
		decreasedItem.setCartItemId(createdCart.getCartItems().getCartItem()
				.get(0).getCartItemId());
		decreasedItem.setQuantity(BigInteger.valueOf(2));
		request.getItems().getItem().add(decreasedItem);

		// increase
		CartModifyRequest.Items.Item increasedItem = new CartModifyRequest.Items.Item();
		increasedItem.setCartItemId(createdCart.getCartItems().getCartItem()
				.get(1).getCartItemId());
		increasedItem.setQuantity(BigInteger.valueOf(4));
		request.getItems().getItem().add(increasedItem);

		// remove
		CartModifyRequest.Items.Item removedItem = new CartModifyRequest.Items.Item();
		removedItem.setCartItemId(createdCart.getCartItems().getCartItem()
				.get(2).getCartItemId());
		removedItem.setQuantity(BigInteger.valueOf(0));
		request.getItems().getItem().add(removedItem);

		Cart modifiedCart = api.cartModify(request);

		assertEquals("2", modifiedCart.getCartItems()
				.getCartItem().get(0).getQuantity());
		assertEquals("4", modifiedCart.getCartItems()
				.getCartItem().get(1).getQuantity());
		assertEquals(createdCart.getCartItems().getCartItem().get(3)
				.getCartItemId(), modifiedCart.getCartItems().getCartItem()
				.get(2).getCartItemId());
		assertEquals(createdCart.getCartItems().getCartItem().size() - 1,
				modifiedCart.getCartItems().getCartItem().size());
	}
	
	@Test(expected = RequestException.class)
	public void testFailCartAdd() throws RequestException {
		CartAddRequest request = new CartAddRequest();

		api.cartAdd(request);
	}

	@Test
	public void testCartAdd() throws RequestException {
		Cart createdCart = null;
		{
			CartCreateRequest request = new CartCreateRequest();

			CartCreateRequest.Items items = new CartCreateRequest.Items();
			request.setItems(items);

			Item item = new Item();
			item.setASIN("383102037X");
			item.setQuantity(BigInteger.valueOf(3));
			items.getItem().add(item);

			createdCart = api.getCartCreate().call(request);
		}

		CartAddRequest request = api.buildCartAddRequest(createdCart);
		request.setItems(new CartAddRequest.Items());
		
		CartAddRequest.Items.Item item = new CartAddRequest.Items.Item();
		item.setASIN("3831019592");
		item.setQuantity(BigInteger.valueOf(1));
		request.getItems().getItem().add(item);
		
		Cart addedCart = api.cartAdd(request);
		
		assertEquals(2, addedCart.getCartItems().getCartItem().size());
		assertEquals("3831019592", addedCart.getCartItems().getCartItem().get(0).getASIN());
	}
	
	@Test(expected = RequestException.class)
	public void testFailCartClear() throws RequestException {
		CartClearRequest request = new CartClearRequest();

		api.cartClear(request);
	}
	
	@Test
	public void testCartClear() throws RequestException {
		Cart createdCart = null;
		{
			CartCreateRequest request = new CartCreateRequest();

			CartCreateRequest.Items items = new CartCreateRequest.Items();
			request.setItems(items);

			Item item = new Item();
			item.setASIN("383102037X");
			item.setQuantity(BigInteger.valueOf(3));
			items.getItem().add(item);

			createdCart = api.getCartCreate().call(request);
		}

		CartClearRequest request = api.buildCartClearRequest(createdCart);
		Cart clearedCart = api.cartClear(request);
		
		assertNull(clearedCart.getCartItems());
	}

	@Test(expected = RequestException.class)
	public void testFailSimilarityLookup() throws RequestException {
		SimilarityLookupRequest lookup = new SimilarityLookupRequest();

		api.getSimilarityLookup().call(lookup);
	}
	
	@Test()
	public void testSimilarityLookup() throws RequestException {
		SimilarityLookupRequest lookup = new SimilarityLookupRequest();
		lookup.getItemId().add("383102037X");
		
		Items items = api.getSimilarityLookup().call(lookup);
		
		assertTrue(! items.getItem().isEmpty());
	}

}
