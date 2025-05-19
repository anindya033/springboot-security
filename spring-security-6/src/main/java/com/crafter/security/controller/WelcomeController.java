package com.crafter.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/security")
public class WelcomeController {
	
	@GetMapping(value="/")
	public String welcome() {
		return "Welcome to spring security...";
	}
	
	@GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
		
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	private final List<Product> productList = new ArrayList<>();

    @PostMapping("/add")
    public String addProduct(@RequestBody Product product) {
        productList.add(product);
        return "Product added successfully!";
    }
    @GetMapping("/get")
    public List<Product> getProduct() {
        return productList;
    }

}

class Product {
    public int productId;
    public String productName;
    public double productPrice;

    public Product() {}

    public Product(int productId, String productName, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
