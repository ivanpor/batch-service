package com.inditex.broker.boot.config;

import java.net.MalformedURLException;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.inditex.broker.api.vo.Articulo;
import com.inditex.broker.api.vo.ArticuloSigrid;
import com.inditex.broker.batch.processors.ArticuloItemProcessor;
import com.inditex.broker.batch.processors.ArticuloItemWriter;
import com.inditex.broker.model.rowmappers.ArticuloSigridRowMapper;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.inditex.broker.api", "com.inditex.broker.batch", "com.inditex.broker.model"})
public class ArticuloBatchConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Value("classpath:schema.sql")
    private Resource schema;
 
    @Value("classpath:data.sql")
    private Resource data;
    
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) throws MalformedURLException {

    	ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(schema);
        databasePopulator.addScript(data);
 
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);
 
        return initializer;
    }
    
	@Bean
    public Job articuloJob(Step articuloStep, JobExecutionListener jobListener) {
        return jobBuilderFactory.get("articuloJob")
            .incrementer(new RunIdIncrementer())
            .listener(jobListener)
            .flow(articuloStep)
            .end()
            .build();
    }

    @Bean
    public Step articuloStep(DataSource dataSource) throws Exception {
        return stepBuilderFactory.get("articuloStep")
            .<ArticuloSigrid, Articulo> chunk(100)
            .reader(reader(dataSource))
            .processor(processor())
            .writer(writer())
            .build();
    }
	
	@Bean
	public ItemReader<ArticuloSigrid> reader(DataSource dataSource) {
		String sql = "SELECT codigo,descripcion,accion FROM articulo_sigrid";
		JdbcCursorItemReader<ArticuloSigrid> databaseReader = new JdbcCursorItemReader<ArticuloSigrid>();
		databaseReader.setDataSource(dataSource);
		databaseReader.setSql(sql);
		databaseReader.setRowMapper(new ArticuloSigridRowMapper());
		return databaseReader;
//		return new ArticuloItemReader();
	}
	
	@Bean
	public ItemProcessor<ArticuloSigrid, Articulo> processor() {
		return new ArticuloItemProcessor(); 
	}
	
	@Bean
	public ItemWriter<Articulo> writer() {
		return new ArticuloItemWriter();
	}
}
