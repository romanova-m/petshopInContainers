package mirea.cart.Service;

import mirea.cart.Domain.Cart;
import mirea.cart.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class CartService {

    private static final Logger LOGGER = Logger.getLogger(CartService.class.getName());
    public final String CONFIG_URL = "http://localhost:8083";
    private Map urls;
    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        urls = restTemplate.getForEntity(CONFIG_URL + "/config",
                Map.class).getBody();
        cartRepository.save(new Cart(1, 1));
        cartRepository.save(new Cart(2, 1));
        cartRepository.save(new Cart(1, 2));
    }

    public Iterable<Cart> cart() {
        return cartRepository.findAll();
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

    public Optional<Cart> cartById(long id) {
        return cartRepository.findById(id);
    }

    public Cart add(Cart cart) {
        cartRepository.save(cart);
        return cart;
    }

    public int getBalanceId(long user_id) {
        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + urls.get("balance") + "/balance", Map[].class);
        LOGGER.info("CartService: getting balance for user " + user_id);
        for (Map map : responseEntity.getBody()) {
            if (map.get("user_id").toString().equals(Long.toString(user_id)))
                return Integer.parseInt(map.get("id").toString());
        }
        return 0;
    }

    public int getBalanceVal(long user_id) {
        int id = getBalanceId(user_id);
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + urls.get("balance") + "/balance/" + id,
                Map.class);
        return responseEntity.getBody() == null? 0 :Integer.parseInt(responseEntity.getBody().get("val").toString());
    }

    public Iterable<Cart> postCart() {
        LOGGER.info("CartService: starting POST cart...");
        long user_id = 1;
        int balance = getBalanceVal(user_id); //
        int sum = getSum(user_id);
        double conversion = getCurrencyById(user_id);
        //
        if (sum != 0){
        if ((sum <= (balance*conversion))) {
            setBalance(user_id, (int) (((balance*conversion) - sum)/conversion));
            rmAll(user_id);
            LOGGER.info("CartService: cart successfully posted for user " + user_id);
        }
        else LOGGER.info("CartService: not enough balance for user " + user_id);}
        else LOGGER.info("No items in cart");
        return cart();
    }

    private int getSum(long user_id) {
        int sum = 0;
        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + urls.get("pet") + "/pet/",
                Map[].class);
        LOGGER.info("CartService: getting order price for user " + user_id);

        for (Cart cart : cart()) {
            if (cart.getUser_id() == user_id)
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
                "http://localhost:" + urls.get("balance") + "/balance/" + id,
                Map.class);
        LOGGER.info("CartService: user(" + user_id + ") updating balance from " +
                responseEntity.getBody().get("val") + " to " + value);

        //responseEntity.getBody().remove("val");
        responseEntity.getBody().put("val", value);


        LOGGER.info("CartService: update " + "http://localhost:" + urls.get("balance") + "/balance/" + id);
        //restTemplate.delete("http://localhost:" + urls.get("balance") + "/balance/" + id);
        restTemplate.put("http://localhost:" + urls.get("balance") + "/balance/", responseEntity);

    }

    public double getCurrencyById(long user_id) {
        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity( "http://localhost:" + urls.get("currency") + "/currency/",
                Map[].class);
        LOGGER.info("CartService: getting currency for user " + user_id);
        for (Map map : responseEntity.getBody()) {
            if (map.get("user_id").toString().equals(Long.toString(user_id)))
                return Double.parseDouble(map.get("conversion").toString());
        }
        return 0;
    }
}
