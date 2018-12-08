package mirea.cart.Service;

import mirea.cart.Domain.Cart;
import mirea.cart.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Controller
public class CartService {

    private CartRepository cartRepository;
    private CartService cartService;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
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
        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/balance/",
                Map[].class);
        for (Map map : responseEntity.getBody()) {
            if (map.get("user_id").toString().equals(Long.toString(user_id)))
                return Integer.parseInt(map.get("id").toString());
        }
        return 0;
    }

    public int getBalanceById(long user_id) {
        int id = getBalanceId(user_id);
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://localhost:8080/balance/" + id,
                Map.class);
        return Integer.parseInt(responseEntity.getBody().get("val").toString());
    }

    public Iterable<Cart> postCart() {
        long user_id = 1;
        int balance = getBalanceById(user_id); //
        int sum = getSum(user_id);
        double conversion = getCurrencyById(user_id);
        //ok
        if ((sum <= balance)) {
            setBalance(user_id, (int) (balance * conversion) - sum);
            cartService.rmAll(user_id);
        }
        return cartService.cart();
    }

    private int getSum(long user_id) {
        int sum = 0;
        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity("http://localhost:8083/pet/",
                Map[].class);
        for (Cart cart : cartService.cart()) {
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
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://localhost:8080/balance/" + id,
                Map.class);

        responseEntity.getBody().remove("val");
        responseEntity.getBody().put("val", value);

        restTemplate.delete("http://localhost:8080/balance/" + id);
        restTemplate.put("http://localhost:8080/balance/", responseEntity);
    }

    public double getCurrencyById(long user_id) {
        ResponseEntity<Map[]> responseEntity = restTemplate.getForEntity("http://localhost:8084/currency/",
                Map[].class);
        for (Map map : responseEntity.getBody()) {
            if (map.get("user_id").toString().equals(Long.toString(user_id)))
                return Double.parseDouble(map.get("conversion").toString());
        }
        return 0;
    }
}
