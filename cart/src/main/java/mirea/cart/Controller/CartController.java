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
    public Iterable<Cart> cart(@RequestAttribute("userId") int userId) {
        return cartService.cart(userId);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    @ResponseBody
    public Iterable<Cart> postCart(@RequestAttribute("userId") int userId) {
        return cartService.postCart(userId);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.PUT)
    @ResponseBody
    public Cart newCart(@RequestBody Cart newCart, @RequestAttribute("userId") int userId) {
        return cartService.add(newCart, userId);
    }

    @RequestMapping(value = "/cart/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delCart(@PathVariable long id) {
        cartService.del(id);
    }
}
