package com.app.ecom.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.ecom.enums.PRODUCT_STATUS;
import com.app.ecom.exception.NotFoundException;
import com.app.ecom.model.Category;
import com.app.ecom.model.Product;
import com.app.ecom.repository.CategoryRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.request.AddProductReq;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product not found"));
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findByStatus(PRODUCT_STATUS.ACTIVE);
    }

    @Override
    public Product addProduct(AddProductReq request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                ()-> new NotFoundException("Category not found")
        );
        Product product = Product
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .sizes(request.getSizes())
                .colors(request.getColors())
                .discountPercent(request.getDiscountPercent())
                .category(category)
                .quantity(request.getQuantity())
                .images(Collections.singletonList(request.getImage()) )
                .build();

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Product not found")
        );
        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }
        if (product.getCategory() != null) {
            existingProduct.setCategory(product.getCategory());
        }
        if (product.getSizes() != null) {
            existingProduct.setSizes(product.getSizes());
        }
        if (product.getColors() != null) {
            existingProduct.setColors(product.getColors());
        }
        if (product.getDiscountPercent() >= 0){
            existingProduct.setDiscountPercent(product.getDiscountPercent());
        }
        if (product.getImages() != null) {
            existingProduct.setImages(product.getImages());
        }
        if(product.getPrice() != 0 ){
            existingProduct.setPrice(product.getPrice());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product =productRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Product not found")
        );
        product.setStatus(PRODUCT_STATUS.DELETED);
        productRepository.save(product);

    }

    @Override
    public Product inactivateProduct(Long id) {
        Product product =productRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Product not found")
        );
        product.setStatus(PRODUCT_STATUS.INACTIVE);
        return productRepository.save(product);
    }

    @Override
    public Product buyProduct(Long id) {
        return null;
    }

    @Override
    public void addQuantity(Long productId, int quantity) {
        Product product =productRepository.findById(productId).orElseThrow(
                ()-> new NotFoundException("Product not found")
        );
        product.setQuantity(quantity);
        productRepository.save(product);
    }

    @Override
    public void removeQuantity(Long productId, int quantity) {
        Product product =productRepository.findById(productId).orElseThrow(
                ()-> new NotFoundException("Product not found")
        );
        int oldQuantity = product.getQuantity();
        int newQuantity = oldQuantity - quantity;
        product.setQuantity(newQuantity);

        productRepository.save(product);
    }
}
