package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()")

public class ShoppingCartController
{
    // a shopping cart controller depends on the service layer
    private ShoppingCartService shoppingCartService;
    private UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping

    public ShoppingCart getCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userService.getByUserName(userName);
            int userId = user.getId();

            // Fetch the cart using the service layer
            return shoppingCartService.getByUserId(userId);
        } catch (Exception e) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, "Oops... error fetching your cart."
            );
        }
    }
        @PostMapping("products/{productId}")
        @ResponseStatus(HttpStatus.CREATED)
        public ShoppingCart addProduct(@PathVariable int productId, Principal principal)
        {
            String userName = principal.getName();
            User user = userService.getByUserName(userName);
            int userId = user.getId();

            // Add the product to the cart via the service layer
            return shoppingCartService.addProduct(userId, productId);
        }


        @PutMapping("products/{productId}")
        public ShoppingCart updateProduct(
        @PathVariable int productId,
        @RequestBody org.yearup.models.ShoppingCartItem item,
        Principal principal
    ) {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        // Update the quantity using the service layer
        return shoppingCartService.updateProductQuantity(userId, productId, item.getQuantity());
    }

        @DeleteMapping
        public ShoppingCart clearCart(Principal principal)
        {
            String userName = principal.getName();
            User user = userService.getByUserName(userName);
            int userId = user.getId();

            // Clear the cart contents using the service layer
            return shoppingCartService.clearCart(userId);
        }
}
