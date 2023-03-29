package com.bb.camelapp.integration.rabbit;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.bb.camelapp.integration.rabbit.RabbitConfig.RABBIT_URI;
import static org.apache.camel.LoggingLevel.INFO;

@Component
public class WeatherRoute extends RouteBuilder {

    @Override
    public void configure() {
        fromF(RABBIT_URI, "weather", "weather")
                .log(INFO, "Before Enrichment: ${body}")
                .unmarshal().json(JsonLibrary.Jackson, WeatherDto.class)
                .process(this::enrichWeatherDto)
                .log(INFO, "After Enrichment: ${body}")
                .marshal().json(JsonLibrary.Jackson, WeatherDto.class)
                .toF(RABBIT_URI, "weather-events", "weather-events")
                .to("file:///home/atom/dev/bb/src/camel/camel/src/?fileName=weather-events.txt&fileExist=Append&appendChars=\n");
    }

    private void enrichWeatherDto(Exchange exchange) {
        WeatherDto dto = exchange.getMessage().getBody(WeatherDto.class);
        dto.setReceivedTime(new Date().toString());

        Message message = new DefaultMessage(exchange);
        message.setBody(dto);
        exchange.setMessage(message);
    }
}
