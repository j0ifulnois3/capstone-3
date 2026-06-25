package org.yearup.models;

import jakarta.persistence.*;

@Entity
@Table(name = "shopping_cart")
public class CartItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private int cartItemId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "quantity")
    private int quantity = 1;

    @Transient
    private Product product;

    // Getter and Setter for the transient product
    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public double getLineTotal()
    {
        if (this.product != null)
        {
            return this.product.getPrice() * this.quantity;
        }
        return 0.0;
    }


    public int getCartItemId()
    {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId)
    {
        this.cartItemId = cartItemId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}