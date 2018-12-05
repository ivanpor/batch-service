package com.testing.microservices.batchservice.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ArticuloChannel {
	
	@Output("sincronize")
	public MessageChannel sincronize();

}
