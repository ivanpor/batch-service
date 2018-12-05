package com.testing.microservices.batchservice.springcloudstreamcomponent;

import java.beans.FeatureDescriptor;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;

/**
 * A wrapper around
 * {@link org.springframework.cloud.stream.config.BindingServiceProperties} that allows
 * the properties provided in the endpoint URI (see
 * {@link SpringCloudStreamEndpointConfiguration}) to override the properties provided via
 * Spring Boot configuration.
 */
public class CamelConfigurableBindingServiceProperties extends BindingServiceProperties {

	private SpringCloudStreamEndpointConfiguration camelConfiguration;

	public CamelConfigurableBindingServiceProperties(BindingServiceProperties bindingServiceProperties) {
		BeanUtils.copyProperties(bindingServiceProperties, this);
	}

	@Override
	public ConsumerProperties getConsumerProperties(String inputBindingName) {
		ConsumerProperties consumerProperties = super.getConsumerProperties(inputBindingName);
		if (camelConfiguration != null) {
			copyNonNullProperties(camelConfiguration, consumerProperties);
		}

		return consumerProperties;
	}

	@Override
	public ProducerProperties getProducerProperties(String outputBindingName) {
		ProducerProperties producerProperties = super.getProducerProperties(
				outputBindingName);
		if (camelConfiguration != null) {
			copyNonNullProperties(camelConfiguration, producerProperties);
		}

		return producerProperties;
	}

	public void setCamelConfiguration(SpringCloudStreamEndpointConfiguration camelConfiguration, String bindingName) {
		this.camelConfiguration = camelConfiguration;
		copyNonNullProperties(camelConfiguration, this);

		BindingProperties bindingProperties = mergeBindingProperties(bindingName);
		mergeAndAddConsumerProperties(bindingName, bindingProperties);
		mergeAndAddProducerProperties(bindingName, bindingProperties);
	}

	private BindingProperties mergeBindingProperties(String bindingName) {
		BindingProperties bindingProperties = Optional
				.ofNullable(this.getBindingProperties(bindingName))
				.orElse(new BindingProperties());

		copyNonNullProperties(this.camelConfiguration, bindingProperties);
		return bindingProperties;
	}

	private void mergeAndAddConsumerProperties(String bindingName,
			BindingProperties bindingProperties) {
		ConsumerProperties consumerProperties = Optional
				.ofNullable(bindingProperties.getConsumer())
				.orElse(new ConsumerProperties());

		copyNonNullProperties(this.camelConfiguration, consumerProperties);

		bindingProperties.setConsumer(consumerProperties);
		this.getBindings().put(bindingName, bindingProperties);
	}

	private void mergeAndAddProducerProperties(String bindingName,
			BindingProperties bindingProperties) {
		ProducerProperties producerProperties = Optional
				.ofNullable(bindingProperties.getProducer())
				.orElse(new ProducerProperties());

		copyNonNullProperties(this.camelConfiguration, producerProperties);

		bindingProperties.setProducer(producerProperties);
		this.getBindings().put(bindingName, bindingProperties);
	}

	private void copyNonNullProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}

	private String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
		return Stream.of(wrappedSource.getPropertyDescriptors())
				.map(FeatureDescriptor::getName).filter(propertyName -> wrappedSource
						.getPropertyValue(propertyName) == null)
				.toArray(String[]::new);
	}
}