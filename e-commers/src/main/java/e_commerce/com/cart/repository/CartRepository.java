package e_commerce.com.cart.repository;

import e_commerce.com.cart.entity.Cart;
import e_commerce.com.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

    boolean existsByUser(User user);
}