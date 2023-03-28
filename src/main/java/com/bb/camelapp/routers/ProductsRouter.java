package com.bb.camelapp.routers;

import com.bb.camelapp.service.ProductsService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class ProductsRouter extends RouteBuilder {

    @Override
    public void configure() {

        restConfiguration()
                .contextPath("/camel")
                .enableCORS(false)
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiContextRouteId("doc-api")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES");

        // Define REST Endpoints
        rest("/camel-products/")
                .get("/{product}")
                .routeId("Get products")
                .bindingMode(RestBindingMode.json)
                .to("direct:hello");

        from("direct:fetchProducts")
                .routeId("direct-fetchProducts")
                .tracing()
                .log(">>> ${body}")
                .bean(ProductsService.class, "fetchProductsByCategory")
                .end();

        from("direct:hello")
                .routeId("direct:hello")
                .tracing()
                .log(">>> ${header.product}")
                .choice()
                .when().simple("${header.product} == 'one'")
                .process(exchange -> {
                    var name = exchange.getIn().getHeader("product");
                    exchange.getIn().setBody("Bay %s".formatted(name));
                })
                .otherwise()
                .process(exchange -> {
                    var name = exchange.getIn().getHeader("product");
                    exchange.getIn().setBody("Hello %s".formatted(name));
                });
    }
}
