package com.edunetcracker.billingservice.ProxyProxy.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class SimpleLoggingFilter implements Filter {

    Logger LOG = LoggerFactory.getLogger(SimpleLoggingFilter.class);

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        LOG.info("Starting a operation for request: {}, headers: {}" ,request.getRequestURI(), request.getHeaderNames());

        filterChain.doFilter(servletRequest, servletResponse);

        LOG.info("Finishing a operation for request: {}, headers: {}, responceStatus: {}" ,request.getRequestURI(), request.getHeaderNames(), response.getStatus());
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}