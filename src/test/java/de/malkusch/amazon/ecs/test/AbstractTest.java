package de.malkusch.amazon.ecs.test;

import java.io.IOException;
import java.util.Properties;

import com.ECS.client.jax.AWSECommerceService;

import de.malkusch.amazon.ecs.ProductAvertisingAPI;
import de.malkusch.amazon.ecs.configuration.PropertiesConfiguration;

abstract public class AbstractTest {

	protected PropertiesConfiguration configuration;
	
	protected ProductAvertisingAPI api;

	public AbstractTest() throws IOException
	{
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/amazon.properties"));
		configuration = new PropertiesConfiguration(properties);
		
		api = new ProductAvertisingAPI(configuration, new AWSECommerceService().getAWSECommerceServicePortDE());
	}

}
