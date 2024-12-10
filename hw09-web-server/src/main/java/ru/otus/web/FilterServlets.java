package ru.otus.web;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("java:S1186")
@WebFilter(urlPatterns = {"/add", "/sub", "/multiply", "/div"})
public class FilterServlets implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(FilterServlets.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        StringBuilder sb = new StringBuilder();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(name);
            sb.append(name).append(" = ").append(value).append("; ");
        }
        logger.info("urlPattern: {}; params: {}", httpServletRequest.getServletPath(), sb);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
