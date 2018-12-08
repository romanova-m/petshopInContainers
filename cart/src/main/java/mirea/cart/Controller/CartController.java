package mirea.cart.Controller;

import mirea.cart.Service.CartService;
import mirea.cart.Domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Cart> cart() {
        return cartService.cart();
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    @ResponseBody
    public Iterable<Cart> postCart() {
        return cartService.postCart();
    }

    @RequestMapping(value = "/cart", method = RequestMethod.PUT)
    @ResponseBody
    public Cart newCart(@RequestBody Cart newCart) {
        return cartService.add(newCart);
    }

    @RequestMapping(value = "/cart/id{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delCart(@PathVariable long id) {
        cartService.del(id);
    }
}
