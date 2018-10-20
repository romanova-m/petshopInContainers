package mirea.pets.controller;

import mirea.pets.service.BalanceService;
import mirea.pets.service.CartService;
import mirea.pets.service.PetService;
import mirea.pets.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private PetService petService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Cart> cart() {
        return cartService.cart();
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Cart> postCart() {
        return cartService.postCart();
    }

    @RequestMapping(value = "/cart/id{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Cart newCart(@PathVariable long id, @RequestBody Cart newCart) {
        return cartService.add(newCart, id);
    }

    @RequestMapping(value = "/cart/id{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delCart(@PathVariable long id) {
        cartService.del(id);
    }
}
