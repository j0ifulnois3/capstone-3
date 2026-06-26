package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService
{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }


        public List<Product> search(Integer categoryId, Double minPrice, Double maxPrice, String subCategory)
        {
            // This hooks up directly to the custom query we found in your Repository!
            return productRepository.search(categoryId, minPrice, maxPrice, subCategory);
        }

    public List<Product> listByCategoryId(int categoryId)
    {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product getById(int productId)
    {
        return productRepository.findById(productId).orElse(null);
    }

    public Product create(Product product)
    {
        product.setProductId(0);
        return productRepository.save(product);
    }

    public Product update(int productId, Product product)
    {
        Product existing = productRepository.findById(productId).orElseThrow();
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setCategoryId(product.getCategoryId());
        existing.setDescription(product.getDescription());
        existing.setSubCategory(product.getSubCategory());


        existing.setStock(product.getStock());

        existing.setFeatured(product.isFeatured());
        existing.setImageUrl(product.getImageUrl());
        return productRepository.save(existing);
    }
    public void delete(int productId)
    {
        productRepository.deleteById(productId);
    }


}
