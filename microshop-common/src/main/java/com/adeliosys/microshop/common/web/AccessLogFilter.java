package com.adeliosys.microshop.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AccessLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        long timestamp = System.currentTimeMillis();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        finally {
            StringBuffer url = req.getRequestURL();
            String queryString = req.getQueryString();
            if (queryString != null) {
                url.append('?').append(queryString);
            }
            int status = res.getStatus();

            LOGGER.info("Served {} '{}' as {} in {} msec", req.getMethod(), url, status, System.currentTimeMillis() - timestamp);
        }
    }

    @Override
    public void destroy() {
    }
}
