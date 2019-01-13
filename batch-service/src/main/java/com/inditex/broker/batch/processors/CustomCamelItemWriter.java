package com.inditex.broker.batch.processors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.spring.batch.support.CamelItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.inditex.broker.api.vo.Mensaje;

public class CustomCamelItemWriter<I extends Mensaje> implements ItemWriter<I> {

    private final Logger LOG = LoggerFactory.getLogger(CamelItemWriter.class);

    private final ProducerTemplate producerTemplate;

    private final String endpointUri;

    public CustomCamelItemWriter(ProducerTemplate producerTemplate, String endpointUri) {
        this.producerTemplate = producerTemplate;
        this.endpointUri = endpointUri;
    }

    @Override
    public void write(List<? extends I> items) throws Exception {
        for (I item : items) {
            LOG.debug("writing item [{}]...", item);
            Map<String,Object> headers = new HashMap<>();
            headers.put("accion",item.getAccion());
            producerTemplate.sendBodyAndHeaders(endpointUri, item, headers);
            LOG.debug("wrote item");
        }
    }
}
