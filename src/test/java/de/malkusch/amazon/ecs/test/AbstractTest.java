package de.malkusch.amazon.ecs.test;

import java.io.IOException;
import java.util.Properties;

import com.ECS.client.jax.AWSECommerceService;

import de.malkusch.amazon.ecs.ProductAdvertisingAPI;
import de.malkusch.amazon.ecs.configuration.PropertiesConfiguration;

abstract public class AbstractTest {

	protected PropertiesConfiguration configuration;

	protected ProductAdvertisingAPI api;

	public AbstractTest() throws IOException {
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/amazon.properties"));
		configuration = new PropertiesConfiguration(properties);

		api = new ProductAdvertisingAPI(configuration,
				new AWSECommerceService().getAWSECommerceServicePortDE());
	}

}
