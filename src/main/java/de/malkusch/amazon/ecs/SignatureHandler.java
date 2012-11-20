package de.malkusch.amazon.ecs;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Node;

public class SignatureHandler implements SOAPHandler<SOAPMessageContext>
{

	public final static String ACCESS_KEY = "AWSAccessKeyId";
	public final static String TIMESTAMP = "Timestamp";
	public final static String SIGNATURE = "Signature";
	public final static String NAMESPACE = "http://security.amazonaws.com/doc/2007-01-01/";
	public final static String SIGN_ALGORITHM = "HmacSHA256";

	private String accessKey;
	private SimpleDateFormat dateFormat ;
	private SecretKeySpec secretKeySpec;
	
	public SignatureHandler(String accessKey, String secretKey) throws UnsupportedEncodingException
	{
		this(accessKey, secretKey.getBytes("UTF-8"));
	}

	public SignatureHandler(String accessKey, byte[] secretKey)
	{
		this.accessKey = accessKey;
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		secretKeySpec = new SecretKeySpec(secretKey, SIGN_ALGORITHM);
	}

	public boolean handleMessage(SOAPMessageContext messagecontext)
	{
		try {
			Boolean outbound = (Boolean) messagecontext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			if (! outbound) {
				return true;

			}
			
			SOAPMessage soapMessage = messagecontext.getMessage();
			SOAPBody soapBody = soapMessage.getSOAPBody();
			Node operation = soapBody.getFirstChild();

			String timeStamp = dateFormat.format(Calendar.getInstance().getTime());
			String signature = getSignature(operation.getLocalName(), timeStamp);

			// Add the authentication headers
			SOAPEnvelope soapEnv = soapMessage.getSOAPPart().getEnvelope();
			SOAPHeader header = soapEnv.getHeader();
			if (header == null) {
				header = soapEnv.addHeader();
				
			}
			addHeader(header, ACCESS_KEY, accessKey);
			addHeader(header, TIMESTAMP, timeStamp);
			addHeader(header, SIGNATURE, signature);

			return true;
			
		} catch (SOAPException e) {
			throw new RuntimeException(e);
			
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);

		}
	}
	
	private void addHeader(SOAPHeader header, String name, String value) throws SOAPException
	{
		header.addChildElement(new QName(NAMESPACE, name)).addTextNode(value);
	}

	/**
	 * Signs as specified by Amazon
	 * 
	 * @see http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/NotUsingWSSecurity.html
	 */
	private String getSignature(String operation, String timeStamp) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException
	{
		String toSign = operation + timeStamp;
		byte[] toSignBytes = toSign.getBytes("UTF-8");

		Mac signer = Mac.getInstance(SIGN_ALGORITHM);
		signer.init(secretKeySpec);
		signer.update(toSignBytes);
		byte[] signBytes = signer.doFinal();

		return DatatypeConverter.printBase64Binary(signBytes);
	}

	public void close(MessageContext messagecontext) {
	}

	public Set<QName> getHeaders() {
		return null;
	}

	public boolean handleFault(SOAPMessageContext messagecontext) {
		return true;
	}
	
}