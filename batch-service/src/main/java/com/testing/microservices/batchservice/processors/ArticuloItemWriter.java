package com.testing.microservices.batchservice.processors;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.spring.batch.support.CamelItemWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.testing.microservices.batchservice.api.Articulo;
import com.testing.microservices.batchservice.api.Mensaje;
import com.testing.microservices.batchservice.properties.ArticuloProperties;

public class ArticuloItemWriter implements ItemWriter<Mensaje<Articulo>> {
	
	@Autowired
	private ProducerTemplate producerTemplate;
	
	@Autowired
	private ArticuloProperties articuloProperties;

	@Override
	public void write(List<? extends Mensaje<Articulo>> items) throws Exception {
	    CamelItemWriter<Mensaje<Articulo>> writer = new CamelItemWriter<Mensaje<Articulo>>(producerTemplate, articuloProperties.getEndpointPointProcessArticulo());
	    writer.write(items);
	}

}
