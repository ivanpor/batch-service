package com.inditex.broker.batch.processors;

import org.springframework.batch.item.ItemProcessor;

import com.inditex.broker.api.vo.Articulo;
import com.inditex.broker.api.vo.ArticuloSigrid;

public class ArticuloItemProcessor implements ItemProcessor<ArticuloSigrid, Articulo>{

	@Override
	public Articulo process(ArticuloSigrid arg0) throws Exception {
		
		if("Articulo33".equals(arg0.getCodigo())) {
			throw new RuntimeException();
		}
		
		return Articulo.builder()
				.codigo(arg0.getCodigo())
				.descripcion(arg0.getDescripcion())
				.accion(arg0.getAccion()).build();
		
	}
}
