package com.inditex.broker.api.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ArticuloProperties {

	@Value("${batchservice.articulos.endpoints.direct.process}")
	private String processArticulosDirect;
	
	@Value("${batchservice.articulos.endpoints.queue.process}")
	private String processArticulosQueue;
	
	@Value("${batchservice.articulos.endpoints.scheluder.search}")
	private String searchArticulosScheluder;
	
	@Value("${batchservice.articulos.endpoints.job.process}")
	private String processArticulosJob;
}
