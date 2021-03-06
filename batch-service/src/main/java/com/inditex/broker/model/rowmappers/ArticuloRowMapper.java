package com.inditex.broker.model.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.inditex.broker.api.vo.Articulo;

public class ArticuloRowMapper implements RowMapper<Articulo> {

	@Override
	public Articulo mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Articulo.builder()
				.codigo(rs.getString("codigo"))
				.descripcion(rs.getString("descripcion"))
				.accion(rs.getShort("accion")).build();
	}

}
