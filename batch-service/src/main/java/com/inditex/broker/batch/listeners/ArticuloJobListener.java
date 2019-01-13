package com.inditex.broker.batch.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticuloJobListener implements JobExecutionListener {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final static Logger LOG = LoggerFactory.getLogger(ArticuloJobListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		Date fecha = jdbcTemplate.queryForObject("SELECT MAX(fecha) FROM configuracion WHERE respuesta = ?", new Object[] {Short.valueOf("2")}, Date.class);
		if(fecha!=null) {
			LOG.info("Ejecutando proceso desde la fecha ".concat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fecha)));
		} else {
			LOG.info("Ejecutando proceso primera vez");
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
	
		if(jobExecution.getStatus().isUnsuccessful()) {
			LOG.info("Se ha producido un error al ejecutar la tarea");
			jdbcTemplate.update("INSERT INTO configuracion (respuesta,fecha) VALUES (?,?)", Short.valueOf("1"), new Date());
		} else {
			LOG.info("La tarea se ha ejecutado correctamente");
			jdbcTemplate.update("INSERT INTO configuracion (respuesta,fecha) VALUES (?,?)", Short.valueOf("2"), new Date());
		}
	}
}
