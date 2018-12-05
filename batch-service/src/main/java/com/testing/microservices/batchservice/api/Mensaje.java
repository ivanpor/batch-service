package com.testing.microservices.batchservice.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Mensaje<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -7899689892107794846L;
	
	private Short accion;
	private T mensaje;
	
	public void setMensaje(T mensaje) {
		this.mensaje=mensaje;
	}

}
