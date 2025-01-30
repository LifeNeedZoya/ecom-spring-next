package com.app.ecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom.model.Product;
import com.app.ecom.request.AddProductReq;
import com.app.ecom.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody AddProductReq request) {
        Product newProduct = productService.addProduct(request);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product) {
        Product updateProduct = productService.updateProduct(product, id);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PreAuthorize("has_role('ROLE_ADMIN')")
    @PostMapping("/inactive")
    public ResponseEntity<String> inactiveProduct(Long id) {
        productService.inactivateProduct(id);
        return ResponseEntity.ok("Product inactivated successfully");
    }

    @PreAuthorize("has_role('ROLE_ADMIN')")
    @PostMapping("/add-quantity")
    public ResponseEntity<String> addProductQuantity(Long id, Integer quantity) {
        productService.addQuantity(id, quantity);
        return ResponseEntity.ok("Successfully added product quantity");
    }

    @PreAuthorize("has_role('ROLE_ADMIN')")
    @PostMapping("/remove-quantity")
    public ResponseEntity<String> removeProductQuantity(Long id, Integer quantity) {
        productService.removeQuantity(id, quantity);
        return ResponseEntity.ok("Successfully removed product quantity");
    }
}
