package com.app.ecom.service;

import com.app.ecom.model.Product;
import com.app.ecom.request.AddProductReq;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product addProduct(AddProductReq req);
    Product updateProduct(Product product, Long id);
    void deleteProduct(Long id);
    Product inactivateProduct(Long id);
    Product buyProduct(Long id);
    void addQuantity(Long productId,int quantity);
    void removeQuantity(Long productId, int quantity);
}
