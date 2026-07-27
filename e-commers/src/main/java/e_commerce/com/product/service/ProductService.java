package e_commerce.com.product.service;

import e_commerce.com.product.dto.ProductRequest;
import e_commerce.com.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);
    List<ProductResponse> getAllProducts();
}