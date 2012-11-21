package de.malkusch.amazon.ecs;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import org.apache.commons.lang3.StringUtils;

import com.ECS.client.jax.AWSECommerceService;
import com.ECS.client.jax.AWSECommerceServicePortType;
import com.ECS.client.jax.Errors;
import com.ECS.client.jax.Errors.Error;
import com.ECS.client.jax.ItemSearch;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;
import com.ECS.client.jax.OperationRequest;

import de.malkusch.amazon.ecs.configuration.Configuration;
import de.malkusch.amazon.ecs.exception.RequestException;

public class ProductAvertisingAPI {

	private AWSECommerceServicePortType port;
	
	private Configuration configuration;
	
	public ProductAvertisingAPI(Configuration configuration) throws UnsupportedEncodingException {
		this(configuration, new AWSECommerceService());
	}

	public ProductAvertisingAPI(Configuration configuration, AWSECommerceService service) throws UnsupportedEncodingException {
		this(configuration, service.getAWSECommerceServicePort());
	}

	public ProductAvertisingAPI(Configuration configuration, AWSECommerceServicePortType port) throws UnsupportedEncodingException {
		this.configuration = configuration;
		this.port = port;
		
		new SignatureHandler(configuration).appendHandler(((BindingProvider) port));
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
		if (items.getRequest().getIsValid().equals("False")) {
			Errors errors = items.getRequest().getErrors();
			if (errors != null && errors.getError() != null) {
				LinkedList<String> errorMessages = new LinkedList<String>();
				for (Error error : errors.getError()) {
					errorMessages.add(error.getMessage());
					
				}
				throw new RequestException(StringUtils.join(errorMessages, '\n'));
				
			}
			throw new RequestException();
			
		}
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

}
