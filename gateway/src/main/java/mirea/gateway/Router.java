package mirea.gateway;

import mirea.logger.CustomLogger;
import mirea.logger.HeaderInterceptor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = {"*"})
public class Router implements javax.servlet.Filter {

    private static final String CONFIG_PORT = "8083";
    private static final Pattern REWRITE_PATTERN = Pattern.compile("(^\\w*[/]*\\d*)$");
    private RestTemplate restTemplate = new RestTemplate();
    private final String SERVICE_ADMIN_TOKEN = "Bearer MSBhZG1pbg==." +
            "ZTdkYjU5YjI0YmZhYjM2ZmY2MzQ2M2FhZGI0OTViZWQyNjk5OTQyNWRlOGE4NzUyOGIwYWRjMGIzZTNiMmQ3OA==";

    @PostConstruct
    private void init(){
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderInterceptor("Authorization", SERVICE_ADMIN_TOKEN));
        interceptors.add(new CustomLogger());

        restTemplate.setInterceptors(interceptors);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
    //this method calling before controller(servlet), every request
        HttpServletRequest request = (HttpServletRequest) req;

        String url = request.getRequestURL().toString();
        String parts[] = url.split("/");
        String service = parts[3];
        String number = null;
        if (parts.length > 4) number = parts[4];
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
        restTemplate.postForObject(url,null, String.class);
    }

    private String getURL(String word, String number) {
        String url = "http://localhost:" + restTemplate.getForObject(
                "http://localhost:" + CONFIG_PORT + "/config", Map.class).get(word) + "/" + word;
        if (number != null) url += "/" + number;
        return url;
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
        //here you may read params from web.xml
    }}
