package mirea.pets.service;

import mirea.pets.domain.Cart;
import mirea.pets.domain.Pet;
import mirea.pets.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Optional;

@Controller
public class CartService {

    private CartRepository cartRepository;
    private BalanceService balanceService;
    private CartService cartService;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

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
        cartRepository.save(new Cart(1,1));
        cartRepository.save(new Cart(2,1));
        cartRepository.save(new Cart(1,2));
    }

    public Iterable<Cart> cart() {
        return cartRepository.findAll();
    }

    public void rmAll(long user_id) {
        long id = searchUser(user_id);
        while (id != -1){
            del(id);
            id = searchUser(user_id);
        }
    }

    public long searchUser(long user_id){
        for (Cart cart: cartRepository.findAll()) {
            if (cart.getUser_id() == user_id) return cart.getId();
        }
        return -1;
    }

    public void del(long id) {
        cartRepository.deleteById(id);
    }

    public Optional<Cart> cartById(long id){
        return cartRepository.findById(id);
    }

    public Cart add(Cart cart) {
        cartRepository.save(cart);
        return cart;
    }


    public Iterable<Cart> postCart() {
        long user_id = 1;
        int sum = 0;
        int balance = balanceService.getBalance(user_id);
        Iterable<Pet> pets = petService.pets();
        for (Cart cart : cartService.cart()) {
            if (cart.getUser_id() == user_id)
                for (Pet pet : pets) {
                    if (cart.getItem_id() == pet.getId()) sum += pet.getPrice();
                }
        }
        if ((sum <= balance)) {
            balanceService.setBalance(user_id, balance - sum);
            cartService.rmAll(user_id);
        }
        return cartService.cart();
    }
}
