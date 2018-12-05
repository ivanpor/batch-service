package com.testing.microservices.batchservice.configurarion;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.testing.microservices.batchservice.api.Articulo;
import com.testing.microservices.batchservice.api.ArticuloSigrid;
import com.testing.microservices.batchservice.api.Mensaje;
import com.testing.microservices.batchservice.processors.ArticuloItemProcessor;
import com.testing.microservices.batchservice.processors.ArticuloItemWriter;
import com.testing.microservices.batchservice.rowmappers.ArticuloSigridRowMapper;

@EnableBatchProcessing
@Configuration
public class ArticuloBatchConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	
//	@Bean
//    public DataSource dataSource() {
//        return DataSourceBuilder
//                .create()
//                .url("jdbc:h2:mem:articulosdb")
//                .username("sa")
//                .password("")
//                .driverClassName("org.h2.Driver")
//                .build();
//    }
	
	@Bean
	public ItemReader<ArticuloSigrid> reader() {
		String sql = "SELECT codigo,descripcion,accion FROM articulo_sigrid";
		JdbcCursorItemReader<ArticuloSigrid> databaseReader = new JdbcCursorItemReader<ArticuloSigrid>();
		databaseReader.setDataSource(dataSource);
		databaseReader.setSql(sql);
		databaseReader.setRowMapper(new ArticuloSigridRowMapper());
		return databaseReader;
	}
	
	@Bean
	public ItemProcessor<ArticuloSigrid, Mensaje<Articulo>> processor() {
		return new ArticuloItemProcessor(); 
	}
	
	@Bean
	public ItemWriter<Mensaje<Articulo>> writer() {
		return new ArticuloItemWriter();
	}

	@Bean
    public Job articuloJob(Step articuloStep, JobExecutionListener jobListener) {
        return jobBuilderFactory.get("importArticuloJob")
            .incrementer(new RunIdIncrementer())
            .listener(jobListener)
            .flow(articuloStep)
            .end()
            .build();
    }

    @Bean
    public Step articuloStep() throws Exception {
        return stepBuilderFactory.get("articuloStep")
            .<ArticuloSigrid, Mensaje<Articulo>> chunk(100)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
    }
}
