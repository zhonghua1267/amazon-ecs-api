package de.malkusch.amazon.ecs;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import org.apache.commons.lang3.StringUtils;

import com.ECS.client.jax.AWSECommerceService;
import com.ECS.client.jax.AWSECommerceServicePortType;
import com.ECS.client.jax.BrowseNodeLookup;
import com.ECS.client.jax.BrowseNodeLookupRequest;
import com.ECS.client.jax.BrowseNodes;
import com.ECS.client.jax.Cart;
import com.ECS.client.jax.CartAdd;
import com.ECS.client.jax.CartAddRequest;
import com.ECS.client.jax.CartClear;
import com.ECS.client.jax.CartClearRequest;
import com.ECS.client.jax.CartCreate;
import com.ECS.client.jax.CartCreateRequest;
import com.ECS.client.jax.CartGet;
import com.ECS.client.jax.CartGetRequest;
import com.ECS.client.jax.CartModify;
import com.ECS.client.jax.CartModifyRequest;
import com.ECS.client.jax.Errors;
import com.ECS.client.jax.Errors.Error;
import com.ECS.client.jax.ItemLookup;
import com.ECS.client.jax.ItemLookupRequest;
import com.ECS.client.jax.ItemSearch;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;
import com.ECS.client.jax.OperationRequest;
import com.ECS.client.jax.Request;
import com.ECS.client.jax.SimilarityLookup;
import com.ECS.client.jax.SimilarityLookupRequest;

import de.malkusch.amazon.ecs.configuration.Configuration;
import de.malkusch.amazon.ecs.exception.RequestException;

/**
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/Welcome.html
 * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/APPNDX_SearchIndexValues.html
 */
public class ProductAvertisingAPI {

	static final public class Condition {
		final public static String NEW = "NEW";
		final public static String USED = "Used";
		final public static String COLLECTIBLE = "Collectible";
		final public static String REFURBISHED = "Refurbished";
		final public static String ALL = "All";
	}

	static final public class IdType {
		final public static String ASIN = "ASIN";
		final public static String SKU = "SKU";
		final public static String UPC = "UPC";
		final public static String EAN = "EAN";
		final public static String ISBN = "ISBN";
	}

	static final public class Boolean {
		final public static String TRUE = "True";
		final public static String FALSE = "False";
	}

	static final public class MerchantId {
		final public static String AMAZON = "Amazon";
	}

	static final public class ResponseGroup {

		static final public class BrowseNode {
			final public static String BROWSE_NODE_INFO = "BrowseNodeInfo";
			final public static String MOST_GIFTED = "MostGifted";
			final public static String NEW_RELEASES = "NewReleases";
			final public static String MOST_WISHED_FOR = "MostWishedFor";
			final public static String TOP_SELLERS = "TopSellers";
		}

		final public static String ACCESSORIES = "Accessories";
		final public static String BROWSE_NODES = "BrowseNodes";
		final public static String EDITORTIAL_REVIEW = "EditorialReview";
		final public static String IMAGES = "Images";
		final public static String ITEM_ATTRIBUTES = "ItemAttributes";
		final public static String ITEM_IDS = "ItemIds";
		final public static String LARGE = "Large";
		final public static String MEDIUM = "Medium";
		final public static String OFFER_FULL = "OfferFull";
		final public static String OFFERS = "Offers";
		final public static String PROMOTION_SUMMARY = "PromotionSummary";
		final public static String OFFER_SUMMARY = "OfferSummary";
		final public static String RELATED_ITEMS = "RelatedItems";
		final public static String REVIEWS = "Reviews";
		final public static String SALES_RANK = "SalesRank";
		final public static String SIMILARITIES = "Similarities";
		final public static String TRACKS = "Tracks";
		final public static String VARIATION_IMAGES = "VariationImages";
		final public static String VARIATIONS = "Variations";
		final public static String VARIATION_SUMMARY = "VariationSummary";
	}

	private AWSECommerceServicePortType port;

	private Configuration configuration;

	public ProductAvertisingAPI(Configuration configuration)
			throws UnsupportedEncodingException {
		this(configuration, new AWSECommerceService());
	}

	public ProductAvertisingAPI(Configuration configuration,
			AWSECommerceService service) throws UnsupportedEncodingException {
		this(configuration, service.getAWSECommerceServicePort());
	}

	public ProductAvertisingAPI(Configuration configuration,
			AWSECommerceServicePortType port)
			throws UnsupportedEncodingException {
		this.configuration = configuration;
		this.port = port;

		new SignatureHandler(configuration)
				.appendHandler(((BindingProvider) port));
	}

	public AWSECommerceServicePortType getPort() {
		return port;
	}
	
	public CartClearRequest buildCartClearRequest(Cart cart) {
		CartClearRequest request = new CartClearRequest();
		request.setHMAC(cart.getHMAC());
		request.setCartId(cart.getCartId());
		return request;
	}
	
	public CartClear buildCartClear() {
		CartClear clear = new CartClear();
		clear.setAssociateTag(configuration.getAssociateTag());
		clear.setAWSAccessKeyId(configuration.getAccessKey());
		return clear;
	}
	
	public CartClear buildCartClear(CartClearRequest request) {
		CartClear clear = buildCartClear();
		clear.getRequest().add(request);
		return clear;
	}
	
	public List<Cart> cartClear(CartClear cartClear) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<Cart>> cart = new Holder<List<Cart>>();
		port.cartClear(cartClear.getMarketplaceDomain(),
				cartClear.getAWSAccessKeyId(), cartClear.getAssociateTag(),
				cartClear.getValidate(), cartClear.getXMLEscaping(),
				cartClear.getShared(), cartClear.getRequest(), operationRequest,
				cart);
		return cart.value;
	}
	
	public Cart cartClear(CartClearRequest request) throws RequestException {
		Cart cart = cartClear(buildCartClear(request)).get(0);
		validateResponse(cart.getRequest());
		return cart;
	}
	
	public CartAddRequest buildCartAddRequest(Cart cart) {
		CartAddRequest request = new CartAddRequest();
		request.setHMAC(cart.getHMAC());
		request.setCartId(cart.getCartId());
		return request;
	}
	
	public CartAdd buildCartAdd() {
		CartAdd add = new CartAdd();
		add.setAssociateTag(configuration.getAssociateTag());
		add.setAWSAccessKeyId(configuration.getAccessKey());
		return add;
	}
	
	public CartAdd buildCartAdd(CartAddRequest request) {
		CartAdd add = buildCartAdd();
		add.getRequest().add(request);
		return add;
	}
	
	public List<Cart> cartAdd(CartAdd cartAdd) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<Cart>> cart = new Holder<List<Cart>>();
		port.cartAdd(cartAdd.getMarketplaceDomain(),
				cartAdd.getAWSAccessKeyId(), cartAdd.getAssociateTag(),
				cartAdd.getValidate(), cartAdd.getXMLEscaping(),
				cartAdd.getShared(), cartAdd.getRequest(), operationRequest,
				cart);
		return cart.value;
	}
	
	public Cart cartAdd(CartAddRequest request) throws RequestException {
		Cart cart = cartAdd(buildCartAdd(request)).get(0);
		validateResponse(cart.getRequest());
		return cart;
	}
	
	public CartModifyRequest buildCartModifyRequest(Cart cart) {
		CartModifyRequest request = new CartModifyRequest();
		request.setHMAC(cart.getHMAC());
		request.setCartId(cart.getCartId());
		return request;
	}
	
	public CartModify buildCartModify() {
		CartModify modify = new CartModify();
		modify.setAssociateTag(configuration.getAssociateTag());
		modify.setAWSAccessKeyId(configuration.getAccessKey());
		return modify;
	}
	
	public CartModify buildCartModify(CartModifyRequest request) {
		CartModify modify = buildCartModify();
		modify.getRequest().add(request);
		return modify;
	}
	
	public List<Cart> cartModify(CartModify cartModify) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<Cart>> cart = new Holder<List<Cart>>();
		port.cartModify(cartModify.getMarketplaceDomain(),
				cartModify.getAWSAccessKeyId(), cartModify.getAssociateTag(),
				cartModify.getValidate(), cartModify.getXMLEscaping(),
				cartModify.getShared(), cartModify.getRequest(), operationRequest,
				cart);
		return cart.value;
	}
	
	public Cart cartModify(CartModifyRequest request) throws RequestException {
		Cart cart = cartModify(buildCartModify(request)).get(0);
		validateResponse(cart.getRequest());
		return cart;
	}
	
	public CartGet buildCartGet() {
		CartGet get = new CartGet();
		get.setAssociateTag(configuration.getAssociateTag());
		get.setAWSAccessKeyId(configuration.getAccessKey());
		return get;
	}
	
	public CartGet buildCartGet(CartGetRequest request) {
		CartGet get = buildCartGet();
		get.getRequest().add(request);
		return get;
	}
	
	public CartGetRequest buildCartGetRequest(Cart cart) {
		CartGetRequest request = new CartGetRequest();
		request.setHMAC(cart.getHMAC());
		request.setCartId(cart.getCartId());
		return request;
	}
	
	public List<Cart> cartGet(CartGet cartGet) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<Cart>> cart = new Holder<List<Cart>>();
		port.cartGet(cartGet.getMarketplaceDomain(),
				cartGet.getAWSAccessKeyId(), cartGet.getAssociateTag(),
				cartGet.getValidate(), cartGet.getXMLEscaping(),
				cartGet.getShared(), cartGet.getRequest(), operationRequest,
				cart);
		return cart.value;
	}
	
	public Cart cartGet(Cart cart) throws RequestException {
		return cartGet(buildCartGetRequest(cart));
	}
	
	public Cart cartGet(CartGetRequest request) throws RequestException {
		Cart cart = cartGet(buildCartGet(request)).get(0);
		validateResponse(cart.getRequest());
		return cart;
	}
	
	public CartCreate buildCartCreate() {
		CartCreate cartCreate = new CartCreate();
		cartCreate.setAssociateTag(configuration.getAssociateTag());
		cartCreate.setAWSAccessKeyId(configuration.getAccessKey());
		return cartCreate;
	}
	
	public CartCreate buildCartCreate(CartCreateRequest request) {
		CartCreate cartCreate = buildCartCreate();
		cartCreate.getRequest().add(request);
		return cartCreate;
	}
	
	public List<Cart> cartCreate(CartCreate cartCreate) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<Cart>> cart = new Holder<List<Cart>>();
		port.cartCreate(cartCreate.getMarketplaceDomain(),
				cartCreate.getAWSAccessKeyId(), cartCreate.getAssociateTag(),
				cartCreate.getValidate(), cartCreate.getXMLEscaping(),
				cartCreate.getShared(), cartCreate.getRequest(), operationRequest,
				cart);
		return cart.value;
	}
	
	public Cart cartCreate(CartCreateRequest request) throws RequestException {
		Cart cart = cartCreate(buildCartCreate(request)).get(0);
		validateResponse(cart.getRequest());
		return cart;
	}

	public BrowseNodeLookup buildBrowseNodeLookup() {
		BrowseNodeLookup lookup = new BrowseNodeLookup();
		lookup.setAWSAccessKeyId(configuration.getAccessKey());
		lookup.setAssociateTag(configuration.getAssociateTag());
		return lookup;
	}

	public BrowseNodeLookup buildBrowseNodeLookup(
			BrowseNodeLookupRequest request) {
		BrowseNodeLookup lookup = buildBrowseNodeLookup();
		lookup.getRequest().add(request);
		return lookup;
	}

	public List<BrowseNodes> browseNodeLookup(BrowseNodeLookup lookup) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<BrowseNodes>> nodes = new Holder<List<BrowseNodes>>();
		port.browseNodeLookup(lookup.getMarketplaceDomain(),
				lookup.getAWSAccessKeyId(), lookup.getAssociateTag(),
				lookup.getValidate(), lookup.getXMLEscaping(),
				lookup.getShared(), lookup.getRequest(), operationRequest,
				nodes);
		return nodes.value;
	}
	
	public BrowseNodes browseNodeLookup(BrowseNodeLookupRequest request) throws RequestException {
		BrowseNodes nodes = browseNodeLookup(buildBrowseNodeLookup(request)).get(0);
		validateResponse(nodes.getRequest());
		return nodes;
	}

	public ItemLookup buildItemLookup() {
		ItemLookup itemLookup = new ItemLookup();
		itemLookup.setAWSAccessKeyId(configuration.getAccessKey());
		itemLookup.setAssociateTag(configuration.getAssociateTag());
		return itemLookup;
	}

	public ItemLookup buildItemLookup(ItemLookupRequest request) {
		ItemLookup itemLookup = buildItemLookup();
		itemLookup.getRequest().add(request);
		return itemLookup;
	}

	public Items itemLookup(ItemLookupRequest request) throws RequestException {
		Items items = itemLookup(buildItemLookup(request)).get(0);
		validateResponse(items.getRequest());
		return items;
	}

	public List<Items> itemLookup(ItemLookup lookup) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<Items>> items = new Holder<List<Items>>();
		port.itemLookup(lookup.getMarketplaceDomain(),
				lookup.getAWSAccessKeyId(), lookup.getAssociateTag(),
				lookup.getValidate(), lookup.getXMLEscaping(),
				lookup.getShared(), lookup.getRequest(), operationRequest,
				items);
		return items.value;
	}
	
	public SimilarityLookup buildSimilarityLookup() {
		SimilarityLookup lookup = new SimilarityLookup();
		lookup.setAWSAccessKeyId(configuration.getAccessKey());
		lookup.setAssociateTag(configuration.getAssociateTag());
		return lookup;
	}
	
	public SimilarityLookup buildSimilarityLookup(SimilarityLookupRequest request) {
		SimilarityLookup lookup = buildSimilarityLookup();
		lookup.getRequest().add(request);
		return lookup;
	}
	
	public List<Items> similarityLookup(SimilarityLookup similarityLookup) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<Items>> items = new Holder<List<Items>>();
		port.similarityLookup(similarityLookup.getMarketplaceDomain(),
				similarityLookup.getAWSAccessKeyId(), similarityLookup.getAssociateTag(),
				similarityLookup.getXMLEscaping(), similarityLookup.getValidate(),
				similarityLookup.getShared(), similarityLookup.getRequest(),
				operationRequest, items);
		return items.value;
	}
	
	public Items similarityLookup(SimilarityLookupRequest request) throws RequestException {
		Items items = similarityLookup(buildSimilarityLookup(request)).get(0);
		validateResponse(items.getRequest());
		return items;
	}

	public ItemSearch buildItemSearch() {
		ItemSearch itemSearch = new ItemSearch();
		itemSearch.setAWSAccessKeyId(configuration.getAccessKey());
		itemSearch.setAssociateTag(configuration.getAssociateTag());
		return itemSearch;
	}

	public ItemSearch buildItemSearch(ItemSearchRequest request) {
		ItemSearch itemSearch = buildItemSearch();
		itemSearch.getRequest().add(request);
		return itemSearch;
	}

	public Items itemSearch(ItemSearchRequest request) throws RequestException {
		Items items = itemSearch(buildItemSearch(request)).get(0);
		validateResponse(items.getRequest());
		return items;
	}

	public List<Items> itemSearch(ItemSearch itemSearch) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<Items>> items = new Holder<List<Items>>();
		port.itemSearch(itemSearch.getMarketplaceDomain(),
				itemSearch.getAWSAccessKeyId(), itemSearch.getAssociateTag(),
				itemSearch.getXMLEscaping(), itemSearch.getValidate(),
				itemSearch.getShared(), itemSearch.getRequest(),
				operationRequest, items);
		return items.value;
	}

	private void validateResponse(Request request) throws RequestException {
		Errors errors = request.getErrors();
		if (errors != null && errors.getError() != null) {
			LinkedList<String> errorMessages = new LinkedList<String>();
			for (Error error : errors.getError()) {
				errorMessages.add(error.getMessage());

			}
			throw new RequestException(StringUtils.join(errorMessages, '\n'));

		}
		if (request.getIsValid().equals(Boolean.FALSE)) {
			throw new RequestException();

		}
	}

}
