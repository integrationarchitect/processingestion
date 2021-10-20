package br.com.processingestion.components;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteComponent extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		from("direct:db-ingestion")
		.transform().constant("{\n"
				+ "\"teste\":\"testando\",\n"
				+ "\"contagem\":123\n"
				+ "}")
		.to("log:log1");
		
	}

}
