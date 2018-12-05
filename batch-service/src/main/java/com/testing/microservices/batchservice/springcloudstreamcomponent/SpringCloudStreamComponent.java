/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.testing.microservices.batchservice.springcloudstreamcomponent;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cloud.stream.binding.BindingTargetFactory;

public class SpringCloudStreamComponent extends UriEndpointComponent implements BeanFactoryAware {

	private ConfigurableListableBeanFactory beanFactory;

	@Autowired
	private CamelBindingService bindingService;

	@Autowired
	private BindingTargetFactory bindingTargetFactory;

	public SpringCloudStreamComponent() {
		super(SpringCloudStreamEndpoint.class);
	}

	public SpringCloudStreamComponent(CamelContext context) {
		super(context, SpringCloudStreamEndpoint.class);
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining,
			Map<String, Object> parameters) throws Exception {
		Endpoint endpoint = new SpringCloudStreamEndpoint(uri, remaining, this,
				createConfiguration(parameters), beanFactory, bindingService,
				bindingTargetFactory);

		return endpoint;
	}

	private SpringCloudStreamEndpointConfiguration createConfiguration(
			Map<String, Object> parameters) throws Exception {
		SpringCloudStreamEndpointConfiguration configuration = new SpringCloudStreamEndpointConfiguration();
		setProperties(configuration, parameters);

		return configuration;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

}