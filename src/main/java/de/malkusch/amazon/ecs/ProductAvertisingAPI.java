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
import com.ECS.client.jax.Errors;
import com.ECS.client.jax.Errors.Error;
import com.ECS.client.jax.ItemLookup;
import com.ECS.client.jax.ItemLookupRequest;
import com.ECS.client.jax.ItemSearch;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;
import com.ECS.client.jax.OperationRequest;
import com.ECS.client.jax.Request;

import de.malkusch.amazon.ecs.configuration.Configuration;
import de.malkusch.amazon.ecs.exception.RequestException;

/**
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 * @see http 
 *      ://docs.amazonwebservices.com/AWSECommerceService/latest/DG/Welcome.html
 * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/
 *      APPNDX_SearchIndexValues.html
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
		List<BrowseNodes> browseNodeLookup = browseNodeLookup(buildBrowseNodeLookup(request));
		if (browseNodeLookup.isEmpty()) {
			return null;
			
		}
		BrowseNodes nodes = browseNodeLookup.get(0);
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
