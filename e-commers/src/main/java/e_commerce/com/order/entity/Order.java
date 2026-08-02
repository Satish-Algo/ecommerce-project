package e_commerce.com.order.entity;

import e_commerce.com.common.enums.OrderStatus;
import e_commerce.com.common.enums.PaymentStatus;
import e_commerce.com.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String orderNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();


    @Column(nullable = false)
    private BigDecimal totalAmount;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    private String shippingAddress;


    private LocalDateTime orderDate;


    @PrePersist
    protected void onCreate(){
        orderDate = LocalDateTime.now();
    }

}