package mirea.items;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebFilter
public class Filter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (true) {
            // cors headers
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Access-Control-Allow-Origin", "http://localhost:3000");
            headers.put("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH");
            headers.put("Access-Control-Allow-Headers", "*");
            headers.put("Access-Control-Max-Age", "600");
            headers.put("Vary", "Accept-Encoding, Origin, Request Method");
            headers.put("Content-Type", "application/json;charset=UTF-8");
            for (String k : headers.keySet()) {
                resp.setHeader(k, headers.get(k));
            }
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}

