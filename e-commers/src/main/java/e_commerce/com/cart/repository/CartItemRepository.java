package e_commerce.com.cart.repository;

import e_commerce.com.cart.entity.Cart;
import e_commerce.com.cart.entity.CartItem;
import e_commerce.com.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    void deleteByCart(Cart cart);
}