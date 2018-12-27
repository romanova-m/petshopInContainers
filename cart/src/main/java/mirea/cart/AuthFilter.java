package mirea.cart;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Logger;

@WebFilter
public class AuthFilter implements javax.servlet.Filter{

    private final Logger LOGGER = Logger.getLogger(AuthFilter.class.getName());
    private final String SECRET_KEY = "SECRET";
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
            if (!isValid(token)) LOGGER.info("Invalid token");
            else {
                LOGGER.info("Successful token verification");
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

    @Override
    public void destroy() {

    }

    private boolean isValid(String token)
    {
        String arr[] = token.split("(\\.)");
        return arr[1].equals(
                Base64.getEncoder().encodeToString(
                        org.apache.commons.codec.digest.DigestUtils.sha256Hex(arr[0] + SECRET_KEY).getBytes()));
    }
}
