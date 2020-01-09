package com.adeliosys.microshop.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Reactive adaptation for Spring Cloud Gateway of the AccessLogFilter in the common module.
 * <p/>
 * Log the duration of requests slower than a certain threshold.
 * <p/>
 * This filter should use a high precedence, but lower than Spring Cloud Sleuth,
 * see {@link org.springframework.cloud.sleuth.instrument.web.TraceHttpAutoConfiguration}
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class AccessLogFilter implements GlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);

    /**
     * Minimum duration of logged request. A negative value means no logging.
     */
    @Value("${microshop.accessLog.threshold:-1}")
    private int threshold;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long start = System.currentTimeMillis();
        return chain.filter(exchange).doAfterTerminate(() -> {
                    long duration = System.currentTimeMillis() - start;
                    if (threshold >= 0 && duration >= threshold) {
                        LOGGER.info("Routed {} '{}' as {} in {} msec",
                                exchange.getRequest().getMethod(),
                                exchange.getRequest().getURI(),
                                exchange.getResponse().getStatusCode().value(),
                                duration);
                    }
                }
        );
    }
}
