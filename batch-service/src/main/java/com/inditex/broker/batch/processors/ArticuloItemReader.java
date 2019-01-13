package com.inditex.broker.batch.processors;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.inditex.broker.api.vo.ArticuloSigrid;
import com.inditex.broker.model.rowmappers.ArticuloSigridRowMapper;

public class ArticuloItemReader implements ItemReader<ArticuloSigrid> {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public ArticuloSigrid read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		String sql = "SELECT codigo,descripcion,accion FROM articulo_sigrid";
		JdbcCursorItemReader<ArticuloSigrid> databaseReader = new JdbcCursorItemReader<ArticuloSigrid>();
		databaseReader.setDataSource(dataSource);
		databaseReader.setSql(sql);
		databaseReader.setRowMapper(new ArticuloSigridRowMapper());
		return databaseReader.read();
	}

}
