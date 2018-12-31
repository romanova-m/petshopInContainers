package mirea.logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Logger;

public class AuthFilter implements Filter {

    private final Logger LOGGER = Logger.getLogger(AuthFilter.class.getName());
    private final String SECRET_KEY = "SECRET";
    private final String SPLIT_PATTERN = "(\\.)";

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
        if (!isValid(token)) {
            LOGGER.info("Invalid token");
            throw new ServletException("Invalid token");
        } else {
            LOGGER.info("Successful token verification.");
            request.setAttribute("userId", getId(token));
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isValid(String token) {
        String arr[] = token.split(SPLIT_PATTERN);
        return arr[1].equals(
                Base64.getEncoder().encodeToString(
                        org.apache.commons.codec.digest.DigestUtils.sha256Hex(arr[0] + SECRET_KEY).getBytes()));
    }

    private long getId(String token) {
        String arr[] = token.split(SPLIT_PATTERN);
        byte[] decodedBytes = Base64.getDecoder().decode(arr[0]);
        return Long.parseLong(new String(decodedBytes).split(" ")[0]);
    }
}
