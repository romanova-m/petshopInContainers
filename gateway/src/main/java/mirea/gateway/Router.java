package mirea.gateway;

import org.springframework.http.HttpRequest;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = {"*"})
public class Router implements javax.servlet.Filter {

    private static final Logger LOGGER = Logger.getLogger(Router.class.getName());
    private static final String CONFIG_PORT = "8083";
    private static final Pattern REWRITE_PATTERN = Pattern.compile("(^\\w*[/]*\\d*)$");

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
    //this method calling before controller(servlet), every request
        LOGGER.info("Filtering request from port" + req.getRemotePort());
        HttpServletRequest request = (HttpServletRequest) req;

        String url = request.getRequestURL().toString();
        String parts[] = url.split("/");
        String service = parts[3];
        String number = null;
        if (parts.length > 4) number = parts[4];

        LOGGER.info("SERVICE:" +service + " NUMBER:"+number + " METHOD:" + request.getMethod());

        Matcher m = REWRITE_PATTERN.matcher(service);
        if(m.find()) {
            HttpServletResponse httpResponse = (HttpServletResponse) res;
            String path = getURL(service, number);
            if (request.getMethod().equals("POST"))
                processPostMethod(path, req); // Here POST method is performed due to POST-GET redirection
            httpResponse.sendRedirect(path);
        } else {
            fc.doFilter(req, res);
        }
    }

    private void processPostMethod(String url, ServletRequest req) {
        LOGGER.info("Router: posting to " + url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,req.getParameterMap(), Map[].class);
    }

    private String getURL(String word, String number) {
        String url = "http://localhost:" + new RestTemplate().getForObject(
                "http://localhost:" + CONFIG_PORT + "/config", Map.class).get(word) + "/" + word;
        if (number != null) url += "/" + number;
        LOGGER.info("Router: redirect URL (" + word + "):" + url);
        return url;
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
        //here you may read params from web.xml
    }}
