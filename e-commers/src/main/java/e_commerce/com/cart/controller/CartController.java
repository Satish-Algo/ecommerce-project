package e_commerce.com.cart.controller;

import e_commerce.com.cart.dto.CartRequest;
import e_commerce.com.cart.dto.CartResponse;
import e_commerce.com.cart.service.CartService;
import e_commerce.com.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(
            @PathVariable Long userId,
            @RequestBody CartRequest request
    ) {

        CartResponse response =
                cartService.addToCart(userId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product added to cart",
                        response
                )
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCart(
            @PathVariable Long userId
    ) {

        CartResponse response =
                cartService.getCart(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart fetched successfully",
                        response
                )
        );
    }

    @PutMapping("/{userId}/{productId}")
    public ResponseEntity<ApiResponse<CartResponse>> updateQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam Integer quantity
    ) {

        CartResponse response =
                cartService.updateQuantity(
                        userId,
                        productId,
                        quantity
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart updated successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(
            @PathVariable Long userId,
            @PathVariable Long productId
    ) {

        cartService.removeItem(userId, productId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Item removed successfully",
                        null
                )
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> clearCart(
            @PathVariable Long userId
    ) {

        cartService.clearCart(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart cleared successfully",
                        null
                )
        );
    }
}