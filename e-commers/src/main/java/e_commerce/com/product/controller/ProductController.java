package e_commerce.com.product.controller;

import e_commerce.com.common.response.ApiResponse;
import e_commerce.com.product.dto.ProductRequest;
import e_commerce.com.product.dto.ProductResponse;
import e_commerce.com.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestBody ProductRequest request
    ) {

        ProductResponse response =
                productService.createProduct(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product created successfully",
                        response
                )
        );
    }
}