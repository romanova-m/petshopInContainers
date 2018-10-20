package mirea.pets.service;

import mirea.pets.domain.Cart;
import mirea.pets.domain.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;

@Controller
public class CartService {
    private HashMap<Long, Cart> map = new HashMap<>();

    private BalanceService balanceService;
    private CartService cartService;

    @Autowired
    public void setBalanceService(BalanceService balanceService){
        this.balanceService = balanceService;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setPetService(PetService petService) {
        this.petService = petService;
    }

    private PetService petService;

    @PostConstruct
    public void init(){
        map.put(Long.valueOf(1), new Cart(1,1,1));
        map.put(Long.valueOf(2), new Cart(2,2,1));
        map.put(Long.valueOf(3), new Cart(3,1,2));
    }

    public Collection<Cart> cart() {
        return map.values();
    }

    public void rmAll(long user_id) {
        long id = searchUser(user_id);
        while (id != -1){
            del(id);
            id = searchUser(user_id);
        }
    }

    public long searchUser(long user_id){
        for (long id: map.keySet()) {
            Cart cart = map.get(id);
            if (cart.getUser_id() == user_id) return id;
        }
        return -1;
    }

    public void del(long id) {
        map.remove(id);
    }

    public Cart cartById(long id){
        return map.get(id);
    }

    public Cart add(Cart newCart, long id) {
        map.put(Long.valueOf(id), newCart);
        return this.cartById(id);
    }


    public Collection<Cart> postCart() {
        long user_id = 1;
        int sum = 0;
        int balance = balanceService.getBalance(user_id);
        Collection<Pet> pets = petService.pets();
        for (Cart cart : cartService.cart()) {
            if (cart.getUser_id() == user_id)
                for (Pet pet : pets) {
                    if (cart.getItem_id() == pet.id) sum += pet.getPrice();
                }
        }
        if ((sum <= balance)) {
            balanceService.setBalance(user_id, balance - sum);
            cartService.rmAll(user_id);
        }
        return cartService.cart();
    }
}
