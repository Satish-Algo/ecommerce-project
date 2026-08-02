package e_commerce.com.order.service;

import e_commerce.com.order.dto.OrderRequest;
import e_commerce.com.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {


    OrderResponse createOrder(OrderRequest request);


    OrderResponse getOrderById(Long orderId);


    List<OrderResponse> getMyOrders();


    List<OrderResponse> getAllOrders();


    OrderResponse updateOrderStatus(Long orderId, String status);

}