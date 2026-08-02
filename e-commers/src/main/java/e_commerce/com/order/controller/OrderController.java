package e_commerce.com.order.controller;

import e_commerce.com.order.dto.OrderRequest;
import e_commerce.com.order.dto.OrderResponse;
import e_commerce.com.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;



    // Create Order
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest request
    ){

        return ResponseEntity.ok(
                orderService.createOrder(request)
        );

    }



    // Get Single Order
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long orderId
    ){

        return ResponseEntity.ok(
                orderService.getOrderById(orderId)
        );

    }



    // User Orders
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders(){

        return ResponseEntity.ok(
                orderService.getMyOrders()
        );

    }



    // Admin All Orders
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );

    }



    // Update Order Status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    ){

        return ResponseEntity.ok(
                orderService.updateOrderStatus(
                        orderId,
                        status
                )
        );

    }

}