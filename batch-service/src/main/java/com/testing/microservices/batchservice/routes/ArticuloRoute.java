package com.testing.microservices.batchservice.routes;

import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArticuloRoute extends SpringRouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("scheduler://articulos?delay=15s")
			.to("spring-batch:articuloJob")
			.log("Ejecutado job: ${body}");
		
		from("direct:processBatch")
			.marshal()
			.json(JsonLibrary.Jackson)
			.to("springcloudstream://sincronize");
	}
}
