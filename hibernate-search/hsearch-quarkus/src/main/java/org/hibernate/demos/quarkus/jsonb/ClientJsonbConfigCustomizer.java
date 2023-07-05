package org.hibernate.demos.quarkus.jsonb;

import jakarta.inject.Singleton;
import jakarta.json.bind.JsonbConfig;

import io.quarkus.jsonb.JsonbConfigCustomizer;

@Singleton
public class ClientJsonbConfigCustomizer implements JsonbConfigCustomizer {

	@Override
	public void customize(JsonbConfig config) {
		config.withFormatting( true );
	}
}
