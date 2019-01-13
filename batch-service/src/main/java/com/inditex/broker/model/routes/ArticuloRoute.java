package com.inditex.broker.model.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.inditex.broker.api.properties.ArticuloProperties;

@Configuration
public class ArticuloRoute extends SpringRouteBuilder {
	
	@Autowired
	private ArticuloProperties articuloProperties;
	
//	@Autowired
//	private Logger logger;
	
	private Logger logger = LoggerFactory.getLogger(ArticuloRoute.class);

	@Override
	public void configure() throws Exception {
		
		//Búsqueda y procesamiento de artículos
		from(articuloProperties.getSearchArticulosScheluder())
			.to(articuloProperties.getProcessArticulosJob())
			.log(LoggingLevel.INFO, logger, "Ejecutado job: ${body}");
		
		//Registro articulos en cola
		from(articuloProperties.getProcessArticulosDirect())
			.marshal()
			.json(JsonLibrary.Jackson)
			.log(LoggingLevel.INFO, logger, "Registrando artículos en cola: ${body}")
			.to(articuloProperties.getProcessArticulosQueue());
	}
}
