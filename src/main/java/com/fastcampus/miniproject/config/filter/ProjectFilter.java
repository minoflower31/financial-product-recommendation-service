package com.fastcampus.miniproject.config.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProjectFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response1 = (HttpServletResponse) response;
        response1.getWriter().write("test");
        chain.doFilter(request, response1);
    }
}
