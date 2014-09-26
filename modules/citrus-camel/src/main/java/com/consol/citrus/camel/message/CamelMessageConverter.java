/*
 * Copyright 2006-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.camel.message;

import com.consol.citrus.camel.endpoint.CamelEndpointConfiguration;
import com.consol.citrus.message.MessageConverter;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.Map;

/**
 * Message converter able to read Camel exchange and create proper Spring Integration message
 * for internal use.
 *
 * @author Christoph Deppisch
 * @since 1.4.1
 */
public class CamelMessageConverter implements MessageConverter<Exchange, CamelEndpointConfiguration> {

    @Override
    public Exchange convertOutbound(Message<?> message, CamelEndpointConfiguration endpointConfiguration) {
        Exchange exchange = new DefaultExchange(endpointConfiguration.getCamelContext());
        convertOutbound(exchange, message, endpointConfiguration);
        return exchange;
    }

    @Override
    public void convertOutbound(Exchange exchange, Message<?> message, CamelEndpointConfiguration endpointConfiguration) {
        org.apache.camel.Message in = exchange.getIn();
        for (Map.Entry<String, Object> header : message.getHeaders().entrySet()) {
            in.setHeader(header.getKey(), header.getValue());
        }
        in.setBody(message.getPayload());
    }

    @Override
    public Message<?> convertInbound(Exchange exchange, CamelEndpointConfiguration endpointConfiguration) {
        if (exchange == null) {
            return null;
        }

        org.apache.camel.Message message;
        if (exchange.hasOut()) {
            message = exchange.getOut();
        } else {
            message = exchange.getIn();
        }

        MessageBuilder messageBuilder = MessageBuilder.withPayload(message.getBody())
                .copyHeaders(message.getHeaders())
                .setHeader(CitrusCamelMessageHeaders.EXCHANGE_ID, exchange.getExchangeId())
                .setHeader(CitrusCamelMessageHeaders.ROUTE_ID, exchange.getFromRouteId())
                .setHeader(CitrusCamelMessageHeaders.EXCHANGE_PATTERN, exchange.getPattern().name())
                .setHeader(CitrusCamelMessageHeaders.EXCHANGE_FAILED, exchange.isFailed());

        //add all exchange properties
        for (Map.Entry<String, Object> property : exchange.getProperties().entrySet()) {
            messageBuilder.setHeader(property.getKey(), property.getValue());
        }

        if (exchange.getException() != null) {
            messageBuilder.setHeader(CitrusCamelMessageHeaders.EXCHANGE_EXCEPTION, exchange.getException().getClass().getName());
            messageBuilder.setHeader(CitrusCamelMessageHeaders.EXCHANGE_EXCEPTION_MESSAGE, exchange.getException().getMessage());
        }

        return messageBuilder.build();
    }
}