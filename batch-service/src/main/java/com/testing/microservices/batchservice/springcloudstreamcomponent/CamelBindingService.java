package com.testing.microservices.batchservice.springcloudstreamcomponent;

import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.config.BindingServiceProperties;

public class CamelBindingService extends BindingService {

	public CamelBindingService(BindingServiceProperties bindingServiceProperties, BinderFactory binderFactory) {
		super(new CamelConfigurableBindingServiceProperties(bindingServiceProperties),binderFactory);
	}
	
	public CamelConfigurableBindingServiceProperties getBindingServiceProperties() {
		return (CamelConfigurableBindingServiceProperties) super.getBindingServiceProperties();
	}
}
