package de.malkusch.amazon.ecs.exception;

import com.ECS.client.jax.Errors.Error;

public class RequestErrorException extends RequestException {
	
	final public static String EXACT_PARAMETER_REQUIREMENT = "AWS.ExactParameterRequirement";
	final public static String EXCEEDED_MAXIMUM_PARAMETER_VALUES = "AWS.ExceededMaximumParameterValues";
	final public static String INSUFFICIENT_PARAMETER_VALUES = "AWS.InsufficientParameterValues";
	final public static String INTERNAL_ERROR = "AWS.InternalError";
	final public static String INVALID_ACCOUNT = "AWS.InvalidAccount";
	final public static String INVALID_ENUMERATED_PARAMETER = "AWS.InvalidEnumeratedParameter";
	final public static String INVALID_ISO8601_TIME = "AWS.InvalidISO8601Time";
	final public static String INVALID_OPERATION_FOR_MARKETPLACE = "AWS.InvalidOperationForMarketplace";
	final public static String INVALID_OPERATION_PARAMETER = "AWS.InvalidOperationParameter";
	final public static String INVALID_PARAMETER_COMBINATION = "AWS.InvalidParameterCombination";
	final public static String INVALID_PARAMETER_VALUE = "AWS.InvalidParameterValue";
	final public static String INVALID_RESPONSE_GROUP = "AWS.InvalidResponseGroup";
	final public static String INVALID_SERVICE_PARAMETER = "AWS.InvalidServiceParameter";
	final public static String INVALID_SUBSCRIPTION_ID = "AWS.InvalidSubscriptionId";
	final public static String MAXIMUM_PARAMETER_REQUIREMENT = "AWS.MaximumParameterRequirement";
	final public static String MINIMUM_PARAMETER_REQUIREMENT = "AWS.MinimumParameterRequirement";
	final public static String MISSING_OPERATION_PARAMETER = "AWS.MissingOperationParameter";
	final public static String MISSING_PARAMETER_COMBINATION = "AWS.MissingParameterCombination";
	final public static String MISSING_PARAMETERS = "AWS.MissingParameters";
	final public static String MISSING_PARAMETER_VALUE_COMBINATION = "AWS.MissingParameterValueCombination";
	final public static String MISSING_SERVICE_PARAMETER = "AWS.MissingServiceParameter";
	final public static String PARAMETER_OUT_OF_RANGE = "AWS.ParameterOutOfRange";
	final public static String PARAMETER_REPEATED_IN_REQUEST = "AWS.ParameterRepeatedInRequest";
	final public static String RESTRICTED_PARAMETER_VALUE_COMBINATION = "AWS.RestrictedParameterValueCombination";
	final public static String ECOMMERCE_SERVICE_EXCEEDED_MAXIMUM_CART_ITEMS = "AWS.ECommerceService.ExceededMaximumCartItems";
	final public static String ECOMMERCE_SERVICE_INVALID_CART_ID = "AWS.ECommerceService.InvalidCartId";
	final public static String ECOMMERCE_SERVICE_INVALID_HMAC = "AWS.ECommerceService.InvalidHMAC";
	final public static String ECOMMERCE_SERVICE_INVALID_QUANTITY = "AWS.ECommerceService.InvalidQuantity";
	final public static String ECOMMERCE_SERVICE_ITEM_ALREADY_IN_CART = "AWS.ECommerceService.ItemAlreadyInCart";
	final public static String ECOMMERCE_SERVICE_ITEM_NOT_ACCESSIBLE = "AWS.ECommerceService.ItemNotAccessible";
	final public static String ECOMMERCE_SERVICE_ITEM_NOT_ELIGIBLE_FOR_CART = "AWS.ECommerceService.ItemNotEligibleForCart";
	final public static String ECOMMERCE_SERVICE_NO_EXACT_MATCHES = "AWS.ECommerceService.NoExactMatches";
	final public static String ECOMMERCE_SERVICE_NO_SIMILARITIES = "AWS.ECommerceService.NoSimilarities";
	final public static String ACCOUNT_LIMIT_EXCEEDED = "AccountLimitExceeded";
	final public static String REQUEST_THROTTLED = "RequestThrottled";
	
	private static final long serialVersionUID = 790813551184073638L;
	
	private Error error;

	public RequestErrorException(Error error) {
		this(error, null);
	}

	public RequestErrorException(Error error, Throwable cause) {
		super(error.getMessage(), cause);
		
		this.error = error;
	}

	/**
	 * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/ErrorMessages.html
	 */
	public String getCode() {
		return error.getCode();
	}
	
}
