package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        return buildCartFromDb(userId);
    }

    private ShoppingCart buildCartFromDb(int userId)
    {
        ShoppingCart cart = new ShoppingCart();

        // 1. Fetch raw cart records from the database using your repository
        java.util.List<CartItem> dbItems = shoppingCartRepository.findByUserId(userId);

        // 2. Turn each database row into a ShoppingCartItem for the frontend
        for (CartItem dbItem : dbItems)
        {
            Product product = productService.getById(dbItem.getProductId());
            if (product != null)
            {
                ShoppingCartItem cartItem = new ShoppingCartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(dbItem.getQuantity());

                cart.add(cartItem);
            }
        }

        return cart;
    }

    public ShoppingCart addProduct(int userId, int productId)
    {
        // Check if this product is already in the user's database cart
        CartItem existingItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existingItem != null)
        {
            // If it's already there, bump up the quantity by 1
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            shoppingCartRepository.save(existingItem);
        }
        else
        {
            // If it's a brand new item, build a new CartItem record
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(1); // start with a quantity of 1

            shoppingCartRepository.save(newItem);
        }

        // Return the fresh, rebuilt shopping cart
        return buildCartFromDb(userId);
    }

    public ShoppingCart updateProductQuantity(int userId, int productId, int quantity)
    {
        CartItem existingItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existingItem != null)
        {
            if (quantity <= 0)
            {
                // If the user drops the quantity to 0 or lower, remove it from the database entirely
                shoppingCartRepository.delete(existingItem);
            }
            else
            {
                // Otherwise, save the new quantity number
                existingItem.setQuantity(quantity);
                shoppingCartRepository.save(existingItem);
            }
        }

        return buildCartFromDb(userId);
    }

    @org.springframework.transaction.annotation.Transactional
    public ShoppingCart clearCart(int userId)
    {
        // Delete all rows matching this userId
        shoppingCartRepository.deleteByUserId(userId);

        // Return an empty shopping cart container
        return new ShoppingCart();
    }
}
