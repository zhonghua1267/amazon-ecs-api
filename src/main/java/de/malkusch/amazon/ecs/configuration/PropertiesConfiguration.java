package de.malkusch.amazon.ecs.configuration;

import java.util.Properties;

/**
 * Provides configuration by Properties
 */
public class PropertiesConfiguration implements Configuration
{
	
	private Properties properties;
	
	public PropertiesConfiguration(Properties properties)
	{
		this.properties = properties;
	}

	/**
	 * Reads amazon.accessKey
	 */
	public String getAccessKey()
	{
		return properties.getProperty("amazon.accessKey");
	}

	/**
	 * Reads amazon.secretKey
	 */
	public String getSecretKey()
	{
		return properties.getProperty("amazon.secretKey");
	}

	/**
	 * Reads amazon.associateTag
	 */
	public String getAssociateTag()
	{
		return properties.getProperty("amazon.associateTag");
	}

}
