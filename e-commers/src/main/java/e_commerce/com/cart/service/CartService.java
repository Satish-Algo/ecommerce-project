package e_commerce.com.cart.service;

import e_commerce.com.cart.dto.CartRequest;
import e_commerce.com.cart.dto.CartResponse;

public interface CartService {

    CartResponse addToCart(
            Long userId,
            CartRequest request
    );

    CartResponse getCart(
            Long userId
    );

    CartResponse updateQuantity(
            Long userId,
            Long productId,
            Integer quantity
    );

    void removeItem(
            Long userId,
            Long productId
    );

    void clearCart(
            Long userId
    );
}