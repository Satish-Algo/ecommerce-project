package e_commerce.com.cart.service;

import e_commerce.com.cart.dto.CartItemResponse;
import e_commerce.com.cart.dto.CartRequest;
import e_commerce.com.cart.dto.CartResponse;
import e_commerce.com.cart.entity.Cart;
import e_commerce.com.cart.entity.CartItem;
import e_commerce.com.cart.repository.CartItemRepository;
import e_commerce.com.cart.repository.CartRepository;
import e_commerce.com.common.exception.ResourceNotFoundException;
import e_commerce.com.product.entity.Product;
import e_commerce.com.product.repository.ProductRepository;
import e_commerce.com.user.entity.User;
import e_commerce.com.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartResponse addToCart(Long userId, CartRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem == null) {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .price(product.getPrice())
                    .build();

        } else {

            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );
        }

        cartItemRepository.save(cartItem);

        return getCart(userId);
    }

    @Override
    public CartResponse getCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        List<CartItem> items = cartItemRepository.findByCart(cart);

        List<CartItemResponse> responses = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;

        int totalItems = 0;

        for (CartItem item : items) {

            BigDecimal itemTotal = item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(itemTotal);

            totalItems += item.getQuantity();

            responses.add(
                    CartItemResponse.builder()
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .imageUrl(item.getProduct().getImageUrl())
                            .price(item.getPrice())
                            .quantity(item.getQuantity())
                            .totalPrice(itemTotal)
                            .build()
            );
        }

        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(user.getId())
                .items(responses)
                .totalAmount(total)
                .totalItems(totalItems)
                .build();
    }

    @Override
    public CartResponse updateQuantity(
            Long userId,
            Long productId,
            Integer quantity
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return getCart(userId);
    }

    @Override
    public void removeItem(
            Long userId,
            Long productId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        cartItemRepository.delete(cartItem);
    }
    @Override
    public void clearCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        cartItemRepository.deleteByCart(cart);
    }
}