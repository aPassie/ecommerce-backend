package com.shanks.E_Commerce.Backend.controller;

import com.shanks.E_Commerce.Backend.model.Order;
import com.shanks.E_Commerce.Backend.model.OrderItem;
import com.shanks.E_Commerce.Backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable String userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItem> getOrderItems(@PathVariable String orderId) {
        return orderService.getOrderItems(orderId);
    }

    @PostMapping("/{userId}")
    public Order createOrder(@PathVariable String userId) {
        return orderService.createOrder(userId);
    }

    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable String orderId, @RequestBody Order orderUpdate) {
        return orderService.updateOrderStatus(orderId, orderUpdate.getStatus());
    }
}
