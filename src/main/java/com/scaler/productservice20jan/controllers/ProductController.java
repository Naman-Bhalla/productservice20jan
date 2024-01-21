package com.scaler.productservice20jan.controllers;

import com.scaler.productservice20jan.dto.User;
import com.scaler.productservice20jan.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController // localhost:8081/products
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/details")  // GET /products/details?user_id={}&product_id={}
    public Product getProductDetails(@RequestParam("user_id") Long userId, @RequestParam("product_id") Long productId) {
        Product product = new Product();
        product.setTitle("iPhone 15 Pro Max");
        product.setImageUrl("htps://localhost:4000");
        product.setPublic(false);
        // assume above product we had got from the database

        if (!product.isPublic()) {
            ResponseEntity<User> response = restTemplate.getForEntity("http://userservice/users/" + userId, User.class);

            User user = response.getBody();

            if (!user.isAdmin()) {
                return null;
            }
        }

        return product;
    }
}
