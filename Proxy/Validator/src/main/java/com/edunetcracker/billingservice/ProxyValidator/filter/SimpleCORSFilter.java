//package com.edunetcracker.billingservice.ProxyValidator.filter;
//
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//@Component
//@Order(1)
//public class SimpleCORSFilter implements Filter {
//
//    Logger LOG = LoggerFactory.getLogger(SimpleCORSFilter.class);
//
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        LOG.info("Starting a operation for request: {}, headers: {}" ,request.getRequestURI(), request.getHeaderNames());
//
//
//        response.setHeader("Access-Control-Allow-Origin","*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, origin, x-requested-with, content-type");
//
//        filterChain.doFilter(servletRequest, servletResponse);
//
//        LOG.info("Finishing a operation for request: {}, headers: {}, responceStatus: {}" ,request.getRequestURI(), request.getHeaderNames(), response.getStatus());
//    }
//
//    public void init(FilterConfig filterConfig) {}
//
//    public void destroy() {}
//
//}