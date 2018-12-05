package com.testing.microservices.batchservice.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Builder
public class Articulo implements Serializable {

	private static final long serialVersionUID = -6678713103696107457L;
	
	private String codigo;
	private String descripcion;
	private Short accion;

}
