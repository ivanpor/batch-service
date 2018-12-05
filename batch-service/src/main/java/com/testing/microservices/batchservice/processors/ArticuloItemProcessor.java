package com.testing.microservices.batchservice.processors;

import org.springframework.batch.item.ItemProcessor;

import com.testing.microservices.batchservice.api.Articulo;
import com.testing.microservices.batchservice.api.ArticuloSigrid;
import com.testing.microservices.batchservice.api.Mensaje;

public class ArticuloItemProcessor implements ItemProcessor<ArticuloSigrid, Mensaje<Articulo>>{

	@Override
	public Mensaje<Articulo> process(ArticuloSigrid arg0) throws Exception {
		
		if(arg0.getCodigo().equals("Articulo33")) {
			throw new RuntimeException();
		}
		
		Articulo articulo = Articulo.builder()
				.codigo(arg0.getCodigo())
				.descripcion(arg0.getDescripcion()).build();
		
		Mensaje<Articulo> mensaje = new Mensaje<Articulo>(arg0.getAccion(),articulo);
		
		return mensaje;
	}
}
