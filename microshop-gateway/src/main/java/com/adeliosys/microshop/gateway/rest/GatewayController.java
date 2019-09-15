package com.adeliosys.microshop.gateway.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GatewayController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Reset the business data in all business services.
     */
    @RequestMapping("/reset")
    public Mono<Map<String, String>> reset() {
        return getServiceResponse("order", "orders")
                .zipWith(getServiceResponse("stock", "articles"),
                        (a, b) -> {
                            Map<String, String> result = new HashMap<>();
                            result.put("order", a);
                            result.put("stock", b);
                            return result;
                        });
    }

    private Mono<String> getServiceResponse(String service, String resource) {
        return webClientBuilder.build()
                .get()
                .uri("http://{service}/api/{resource}/reset", service, resource)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("error: " + e.toString()));
    }
}
