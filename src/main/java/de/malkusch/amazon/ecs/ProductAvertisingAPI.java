package de.malkusch.amazon.ecs;

import java.io.UnsupportedEncodingException;

import javax.xml.ws.BindingProvider;

import com.ECS.client.jax.AWSECommerceService;
import com.ECS.client.jax.AWSECommerceServicePortType;

import de.malkusch.amazon.ecs.call.BrowseNodeLookupCall;
import de.malkusch.amazon.ecs.call.CartAddCall;
import de.malkusch.amazon.ecs.call.CartClearCall;
import de.malkusch.amazon.ecs.call.CartCreateCall;
import de.malkusch.amazon.ecs.call.CartGetCall;
import de.malkusch.amazon.ecs.call.CartModifyCall;
import de.malkusch.amazon.ecs.call.ItemLookupCall;
import de.malkusch.amazon.ecs.call.ItemSeachCall;
import de.malkusch.amazon.ecs.call.SimilarityLookupCall;
import de.malkusch.amazon.ecs.configuration.Configuration;

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
	private ItemSeachCall itemSearch = new ItemSeachCall(this);
	private SimilarityLookupCall similarityLookup = new SimilarityLookupCall(
			this);
	private ItemLookupCall itemLookup = new ItemLookupCall(this);
	private BrowseNodeLookupCall browseNodeLookup = new BrowseNodeLookupCall(
			this);
	private CartCreateCall cartCreate = new CartCreateCall(this);
	private CartGetCall cartGet = new CartGetCall(this);
	private CartClearCall cartClear = new CartClearCall(this);
	private CartModifyCall cartModify = new CartModifyCall(this);
	private CartAddCall cartAdd = new CartAddCall(this);

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

	public CartAddCall getCartAdd() {
		return cartAdd;
	}

	public CartModifyCall getCartModify() {
		return cartModify;
	}

	public CartClearCall getCartClear() {
		return cartClear;
	}

	public CartGetCall getCartGet() {
		return cartGet;
	}

	public CartCreateCall getCartCreate() {
		return cartCreate;
	}

	public BrowseNodeLookupCall getBrowseNodeLookup() {
		return browseNodeLookup;
	}

	public ItemLookupCall getItemLookup() {
		return itemLookup;
	}

	public SimilarityLookupCall getSimilarityLookup() {
		return similarityLookup;
	}

	public ItemSeachCall getItemSearch() {
		return itemSearch;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

}
