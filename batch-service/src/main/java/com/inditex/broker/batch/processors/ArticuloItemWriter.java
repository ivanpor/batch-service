package com.inditex.broker.batch.processors;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.inditex.broker.api.properties.ArticuloProperties;
import com.inditex.broker.api.vo.Articulo;


public class ArticuloItemWriter implements ItemWriter<Articulo> {
	
	@Autowired
	private ProducerTemplate producerTemplate;
	
	@Autowired
	private ArticuloProperties articuloProperties;

	@Override
	public void write(List<? extends Articulo> items) throws Exception {
		CustomCamelItemWriter<Articulo> writer = new CustomCamelItemWriter<>(producerTemplate, articuloProperties.getProcessArticulosDirect());
	    writer.write(items);
	}
}
