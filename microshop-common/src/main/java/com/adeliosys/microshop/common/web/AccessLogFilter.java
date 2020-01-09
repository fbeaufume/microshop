package com.adeliosys.microshop.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Log the duration of requests slower than a certain threshold.
 * <p/>
 * This filter should use a high precedence, but lower than Spring Cloud Sleuth,
 * see {@link org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration}
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class AccessLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);

    /**
     * Minimum duration of logged request. A negative value means no logging.
     */
    @Value("${microshop.accessLog.threshold:-1}")
    private int threshold;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        long duration = -System.currentTimeMillis();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            duration += System.currentTimeMillis();

            if (threshold >= 0 && duration >= threshold) {
                StringBuffer url = req.getRequestURL();
                String queryString = req.getQueryString();
                if (queryString != null) {
                    url.append('?').append(queryString);
                }
                int status = res.getStatus();

                LOGGER.info("Served {} '{}' as {} in {} msec", req.getMethod(), url, status, duration);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
