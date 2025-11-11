package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "APIs for managing Orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a new order", description = "Place a new order with order items")
    public ResponseEntity createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Map<String, Object> result = new HashMap<>();
        orderService.createOrder(orderDTO, result);
        return result.containsKey("success") ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve all orders for the logged-in user or admin")
    public ResponseEntity getAllOrders() {
        Map<String, Object> result = new HashMap<>();
        orderService.getAllOrders(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by its ID")
    public ResponseEntity getOrderById(@PathVariable Long orderId) {
        Map<String, Object> result = new HashMap<>();
        orderService.getOrderById(orderId, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Update an order", description = "Update order status or modify order items")
    public ResponseEntity updateOrder(@PathVariable Long orderId, @Valid @RequestBody OrderDTO orderDTO) {
        Map<String, Object> result = new HashMap<>();
        orderService.updateOrder(orderId, orderDTO, result);
        return result.containsKey("success") ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Cancel an order", description = "Cancel an existing order")
    public ResponseEntity cancelOrder(@PathVariable Long orderId) {
        Map<String, Object> result = new HashMap<>();
        orderService.cancelOrder(orderId, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
