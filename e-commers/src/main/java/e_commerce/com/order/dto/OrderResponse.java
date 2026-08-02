package e_commerce.com.order.dto;

import e_commerce.com.common.enums.OrderStatus;
import e_commerce.com.common.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {


    private Long id;

    private String orderNumber;

    private BigDecimal totalAmount;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private String shippingAddress;

    private LocalDateTime orderDate;

    private List<OrderItemResponse> items;


}