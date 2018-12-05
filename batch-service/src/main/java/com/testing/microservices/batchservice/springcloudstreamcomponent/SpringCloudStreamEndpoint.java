package com.testing.microservices.batchservice.springcloudstreamcomponent;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.util.ObjectHelper;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cloud.stream.binding.BindingTargetFactory;

public class SpringCloudStreamEndpoint extends DefaultEndpoint {

	private String destination;

	private final SpringCloudStreamEndpointConfiguration configuration;
	private final ConfigurableListableBeanFactory beanFactory;
	private final CamelBindingService bindingService;
	private final BindingTargetFactory bindingTargetFactory;
	
	public SpringCloudStreamEndpoint(String uri, String destination,
			SpringCloudStreamComponent component,
			SpringCloudStreamEndpointConfiguration configuration,
			ConfigurableListableBeanFactory beanFactory,
			CamelBindingService bindingService,
			BindingTargetFactory bindingTargetFactory) {
		super(uri, component);

		this.destination = ObjectHelper.notNull(destination, "destination");
		this.configuration = ObjectHelper.notNull(configuration, "configuration");
		this.beanFactory = beanFactory;
		this.bindingService = bindingService;
		this.bindingTargetFactory = bindingTargetFactory;
	}

    public Producer createProducer() throws Exception {
    	bindingService.getBindingServiceProperties().setCamelConfiguration(configuration, getDestination());
        return new SpringCloudStreamProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	bindingService.getBindingServiceProperties().setCamelConfiguration(configuration, getDestination());
        return new SpringCloudStreamConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }

	public String getDestination() {
		return destination;
	}

	public SpringCloudStreamEndpointConfiguration getConfiguration() {
		return configuration;
	}

	public ConfigurableListableBeanFactory getBeanFactory() {
		return beanFactory;
	}

	public CamelBindingService getBindingService() {
		return bindingService;
	}

	public BindingTargetFactory getBindingTargetFactory() {
		return bindingTargetFactory;
	}
    
    
}
