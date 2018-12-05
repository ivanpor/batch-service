package com.testing.microservices.batchservice.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ArticuloProperties {

	@Value("${batchservice.endpoints.processArticulo}")
	private String endpointPointProcessArticulo;
}
