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

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * The HelloWorld producer.
 */
public class SpringCloudStreamProducer extends DefaultProducer {
	
    private static final transient Logger LOG = LoggerFactory.getLogger(SpringCloudStreamProducer.class);
    private SpringCloudStreamEndpoint endpoint;

    public SpringCloudStreamProducer(SpringCloudStreamEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
    	SubscribableChannel channel;

		// reuse the existing binding target
		if (endpoint.getBeanFactory().containsBean(endpoint.getDestination())) {
			channel = endpoint.getBeanFactory().getBean(endpoint.getDestination(),
					SubscribableChannel.class);
		}
		else {
			channel = createOutputBindingTarget();
			endpoint.getBindingService().bindProducer(channel, endpoint.getDestination());
		}

		Message message = exchange.getIn();
		if (message.getBody() != null) {
			channel.send(MessageBuilder.withPayload(message.getBody()).copyHeadersIfAbsent(message.getHeaders()).build());
		}
		else {
			log.warn("Message body is null, ignoring");
		} 
    }
    
	private SubscribableChannel createOutputBindingTarget() {
		SubscribableChannel channel = (SubscribableChannel) endpoint.getBindingTargetFactory().createOutput(endpoint.getDestination());
		endpoint.getBeanFactory().registerSingleton(endpoint.getDestination(), channel);
		channel = (SubscribableChannel) endpoint.getBeanFactory().initializeBean(channel, endpoint.getDestination());
		return channel;
	}


}
