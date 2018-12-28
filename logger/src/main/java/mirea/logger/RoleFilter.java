package mirea.logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;

public class RoleFilter implements Filter{
    private final Logger LOGGER = Logger.getLogger(AuthFilter.class.getName());
    private final String SPLIT_PATTERN = "(\\.)";
    private final String USER_METHODS[] = {"GET"}; // All other methods will be filtered

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String authHeader = request.getHeader("authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header");
        }
        final String token = authHeader.substring(7);

        if (!isUserMethod(request.getMethod())) {
            if (isAdmin(token)) {
                LOGGER.info("ROLE ADMIN CONFIRMED");
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                LOGGER.info("REQUIRED ADMIN RIGHTS");
                throw new ServletException("Permission denied");
            }
        }
        else filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isUserMethod(String method) {
        return Arrays.stream(USER_METHODS).anyMatch(method::equals);
    }

    @Override
    public void destroy() {

    }

    private String role(String token)
    {
        String arr[] = token.split(SPLIT_PATTERN);
        byte[] decodedBytes = Base64.getDecoder().decode(arr[0]);
        return new String(decodedBytes).split(" ")[1];
    }

    private boolean isAdmin (String token)
    {
        return "admin".equals(role(token));
    }
}
