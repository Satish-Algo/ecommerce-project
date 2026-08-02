package e_commerce.com.order.repository;

import e_commerce.com.order.entity.Order;
import e_commerce.com.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByOrderStatus(String orderStatus);
}