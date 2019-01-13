package com.inditex.broker.model.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.inditex.broker.api.vo.ArticuloSigrid;

public class ArticuloSigridRowMapper implements RowMapper<ArticuloSigrid> {

	@Override
	public ArticuloSigrid mapRow(ResultSet rs, int rowNum) throws SQLException {
		return ArticuloSigrid.builder()
				.codigo(rs.getString("codigo"))
				.descripcion(rs.getString("descripcion"))
				.accion(rs.getShort("accion")).build();
	}

}
