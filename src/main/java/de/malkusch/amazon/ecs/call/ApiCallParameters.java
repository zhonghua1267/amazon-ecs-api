package de.malkusch.amazon.ecs.call;

import java.util.List;

public interface ApiCallParameters<RequestType> {

	public void setAWSAccessKeyId(String awsAccessKeyId);

	public void setAssociateTag(String associateTag);

	public List<RequestType> getRequest();

}
