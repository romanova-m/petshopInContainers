package mirea.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CartService {

    public final String CONFIG_URL = "http://config:8083";
    private final String SERVICE_ADMIN_TOKEN = "Bearer MSBhZG1pbg==." +
            "ZTdkYjU5YjI0YmZhYjM2ZmY2MzQ2M2FhZGI0OTViZWQyNjk5OTQyNWRlOGE4NzUyOGIwYWRjMGIzZTNiMmQ3OA==";

    @Nullable private Map urls;
    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        cartRepository.save(new Cart(1, 1));
        cartRepository.save(new Cart(2, 1));
        cartRepository.save(new Cart(1, 2));
        urls = getUrlsAttempt();
    }

    private Map getUrlsAttempt() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new CustomLogger());

        restTemplate.setInterceptors(interceptors);

        try {
            return restTemplate.getForEntity(CONFIG_URL + "/config",
                    Map.class).getBody();
        }
        catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Iterable<Cart> cart(long user_id) { return cartByUser(user_id);}

    Iterable<Cart> cartByUser(long user_id){
        if (urls == null) urls = getUrlsAttempt();
        Iterable<Cart> carts = cartRepository.findAll();
        Iterable<Cart> result = new ArrayList<Cart>();
        for (Cart cart: carts) {
            if (cart.getUser_id() == user_id) ((ArrayList<Cart>) result).add(cart);
        }
        return result;
    }

    public void rmAll(long user_id) {
        long id = searchUser(user_id);
        while (id != -1) {
            del(id);
            id = searchUser(user_id);
        }
    }

    public long searchUser(long user_id) {
        for (Cart cart : cartRepository.findAll()) {
            if (cart.getUser_id() == user_id) return cart.getId();
        }
        return -1;
    }

    public void del(long id) {
        cartRepository.deleteById(id);
    }

    public Cart add(Cart cart, long user_id) {
        cart.setUser_id(user_id);
        cartRepository.save(cart);
        return cart;
    }

    public int getBalanceId(long user_id) {
        if (urls == null) urls = getUrlsAttempt();
        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity(
                "http://balance:" + urls.get("balance") + "/balance", Map[].class);
        for (Map map : responseEntity.getBody()) {
            if (map.get("user_id").toString().equals(Long.toString(user_id)))
                return Integer.parseInt(map.get("id").toString());
        }
        return 0;
    }

    public int getBalanceVal(long user_id) {
        int id = getBalanceId(user_id);
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(
                "http://balance:" + urls.get("balance") + "/balance/" + id,
                Map.class);
        return responseEntity.getBody() == null? 0 :Integer.parseInt(responseEntity.getBody().get("val").toString());
    }

    public Iterable<Cart> postCart(long user_id) throws Exception {
        if (urls == null) {
            urls = getUrlsAttempt();
            if (urls == null) throw new Exception("urls not available");
        }
        int balance = getBalanceVal(user_id); //
        int sum = getSum(user_id);
        double conversion = getCurrencyById(user_id);
        //
        if (sum != 0){
            if ((sum <= (balance*conversion))) {
                setBalance(user_id, (int) (((balance*conversion) - sum)/conversion));
                rmAll(user_id);
            }
        }
        return cart(user_id);
    }

    private int getSum(long user_id) {
        int sum = 0;
        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity(
                "http://items:" + urls.get("pet") + "/pet/",
                Map[].class);

        for (Cart cart : cart(user_id)) {
                for (Map map : responseEntity.getBody()) {
                    if (Long.toString(cart.getItem_id()).equals(map.get("id").toString()))
                        sum += Integer.parseInt(map.get("price").toString());
                }
        }
        return sum;
    }

    private void setBalance(long user_id, int value) {
        int id = getBalanceId(user_id);
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(
                "http://balance:" + urls.get("balance") + "/balance/" + id,
                Map.class);

        responseEntity.getBody().put("val", value);
        restTemplate.put("http://balance:" + urls.get("balance") + "/balance/", responseEntity);

    }

    public double getCurrencyById(long user_id) {
        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity( "http://currency:" + urls.get("currency") + "/currency/",
                Map[].class);
        for (Map map : responseEntity.getBody()) {
            if (map.get("user_id").toString().equals(Long.toString(user_id)))
                return Double.parseDouble(map.get("conversion").toString());
        }
        return 0;
    }
}
