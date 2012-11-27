package de.malkusch.amazon.ecs.call;

import java.util.List;

import javax.xml.ws.Holder;

import com.ECS.client.jax.Errors;
import com.ECS.client.jax.Errors.Error;
import com.ECS.client.jax.OperationRequest;
import com.ECS.client.jax.Request;

import de.malkusch.amazon.ecs.InterfaceDecorator;
import de.malkusch.amazon.ecs.ProductAvertisingAPI;
import de.malkusch.amazon.ecs.ProductAvertisingAPI.Boolean;
import de.malkusch.amazon.ecs.exception.RequestException;

/**
 * @author Markus Malkusch <markus@malkusch.de>
 */
abstract public class ApiCall<CallType, RequestType, ResultType> {

	protected ProductAvertisingAPI api;

	private Class<CallType> callClass;

	abstract protected void call(CallType call,
			Holder<OperationRequest> operationRequest,
			Holder<List<ResultType>> result);

	public ApiCall(ProductAvertisingAPI api, Class<CallType> callClass) {
		this.api = api;
		this.callClass = callClass;
	}

	public List<ResultType> callRequests(CallType call) {
		Holder<OperationRequest> operationRequest = null;
		Holder<List<ResultType>> result = new Holder<List<ResultType>>();
		call(call, operationRequest, result);
		return result.value;
	}

	public ResultType call(RequestType request) throws RequestException {
		ResultType result = callRequests(buildCall(request)).get(0);
		ApiResponse response = InterfaceDecorator.getProxy(result,
				ApiResponse.class);
		validateResponse(response.getRequest());
		return result;
	}

	public CallType buildCall() {
		try {
			CallType instance = callClass.newInstance();
			ApiCallParameters<?> parameter = InterfaceDecorator.getProxy(
					instance, ApiCallParameters.class);
			parameter.setAssociateTag(api.getConfiguration().getAssociateTag());
			parameter.setAWSAccessKeyId(api.getConfiguration().getAccessKey());
			return instance;

		} catch (InstantiationException e) {
			throw new RuntimeException(e);

		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);

		}
	}

	public CallType buildCall(RequestType request) {
		CallType instance = buildCall();
		ApiCallParameters<RequestType> parameter = InterfaceDecorator.getProxy(
				instance, ApiCallParameters.class);
		parameter.getRequest().add(request);
		return instance;
	}

	private void validateResponse(Request request) throws RequestException {
		Errors errors = request.getErrors();
		if (errors != null && errors.getError() != null) {
			StringBuffer errorMessages = new StringBuffer();
			for (Error error : errors.getError()) {
				errorMessages.append(error.getMessage()).append('\n');

			}
			throw new RequestException(errorMessages.toString());

		}
		if (request.getIsValid().equals(Boolean.FALSE)) {
			throw new RequestException();

		}
	}

}
