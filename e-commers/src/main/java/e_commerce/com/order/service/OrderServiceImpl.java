package e_commerce.com.order.service;

import e_commerce.com.cart.entity.Cart;
import e_commerce.com.cart.entity.CartItem;
import e_commerce.com.cart.repository.CartRepository;
import e_commerce.com.common.enums.OrderStatus;
import e_commerce.com.common.enums.PaymentStatus;
import e_commerce.com.common.exception.BadRequestException;
import e_commerce.com.common.exception.ResourceNotFoundException;
import e_commerce.com.order.dto.OrderItemResponse;
import e_commerce.com.order.dto.OrderRequest;
import e_commerce.com.order.dto.OrderResponse;
import e_commerce.com.order.entity.Order;
import e_commerce.com.order.entity.OrderItem;
import e_commerce.com.order.repository.OrderRepository;
import e_commerce.com.user.entity.User;
import e_commerce.com.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final UserRepository userRepository;



    @Override
    public OrderResponse createOrder(OrderRequest request) {


        User user = getCurrentUser();


        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Cart not found"
                        )
                );


        if(cart.getItems().isEmpty()){

            throw new BadRequestException(
                    "Cart is empty"
            );

        }



        Order order = Order.builder()
                .orderNumber("ORD-" + UUID.randomUUID())
                .user(user)
                .orderStatus(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .shippingAddress(request.getShippingAddress())
                .build();



        BigDecimal totalAmount = BigDecimal.ZERO;



        for(CartItem cartItem : cart.getItems()){


            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getProduct().getPrice())
                    .subtotal(
                            cartItem.getProduct()
                                    .getPrice()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    cartItem.getQuantity()
                                            )
                                    )
                    )
                    .build();



            order.getOrderItems()
                    .add(orderItem);



            totalAmount =
                    totalAmount.add(
                            orderItem.getSubtotal()
                    );

        }



        order.setTotalAmount(totalAmount);



        Order savedOrder =
                orderRepository.save(order);



        cart.getItems().clear();

        cartRepository.save(cart);



        return mapToResponse(savedOrder);

    }





    @Override
    public OrderResponse getOrderById(Long orderId) {


        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Order not found"
                                )
                        );


        return mapToResponse(order);

    }





    @Override
    public List<OrderResponse> getMyOrders() {


        User user = getCurrentUser();


        return orderRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }





    @Override
    public List<OrderResponse> getAllOrders() {


        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }





    @Override
    public OrderResponse updateOrderStatus(
            Long orderId,
            String status
    ) {


        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Order not found"
                                )
                        );


        order.setOrderStatus(
                OrderStatus.valueOf(status)
        );


        return mapToResponse(
                orderRepository.save(order)
        );

    }





    private User getCurrentUser(){


        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();



        String email =
                authentication.getName();



        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found"
                        )
                );

    }





    private OrderResponse mapToResponse(Order order){


        List<OrderItemResponse> items =
                order.getOrderItems()
                        .stream()
                        .map(item -> OrderItemResponse.builder()
                                .productId(
                                        item.getProduct().getId()
                                )
                                .productName(
                                        item.getProduct().getName()
                                )
                                .quantity(
                                        item.getQuantity()
                                )
                                .price(
                                        item.getPrice()
                                )
                                .subtotal(
                                        item.getSubtotal()
                                )
                                .build()
                        )
                        .collect(Collectors.toList());



        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .shippingAddress(order.getShippingAddress())
                .orderDate(order.getOrderDate())
                .items(items)
                .build();

    }

}