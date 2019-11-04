package org.hibernate.demos.quarkus.jsonb;

import javax.inject.Singleton;
import javax.json.bind.JsonbConfig;

import io.quarkus.jsonb.JsonbConfigCustomizer;

@Singleton
public class ClientJsonbConfigCustomizer implements JsonbConfigCustomizer {

	@Override
	public void customize(JsonbConfig config) {
		config.withFormatting( true );
	}
}
