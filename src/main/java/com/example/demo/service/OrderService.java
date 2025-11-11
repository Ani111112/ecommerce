package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.entities.Item;
import com.example.demo.entities.Order;
import com.example.demo.entities.OrderItem;
import com.example.demo.entities.User;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    public void createOrder(OrderDTO orderDTO, Map<String, Object> result) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Enum.valueOf(com.example.demo.Enum.OrderStatus.class, orderDTO.getStatus()));
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItemList = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            Item item = itemRepository.findById(itemDTO.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemDTO.getItemId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrder(order);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPriceAtPurchase(item.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));

            totalAmount = totalAmount.add(orderItem.getPriceAtPurchase());
            orderItemList.add(orderItem);
        }

        order.setOrderItemList(orderItemList);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        List<OrderItem> savedOrderItem = orderItemRepository.saveAll(orderItemList);

        result.put("success", true);
        result.put("orderId", savedOrder.getId());
        result.put("message", "Order created successfully");
    }

    public void getAllOrders(Map<String, Object> result) {
        List<Order> orders = orderRepository.findAll();
        result.put("orders", orders);
    }

    public void getOrderById(Long orderId, Map<String, Object> result) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        result.put("order", order);
    }

    public void updateOrder(Long orderId, OrderDTO orderDTO, Map<String, Object> result) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (orderDTO.getStatus() != null) {
            order.setStatus(Enum.valueOf(com.example.demo.Enum.OrderStatus.class, orderDTO.getStatus()));
        }

        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            orderItemRepository.deleteAll(order.getOrderItemList());

            List<OrderItem> newItems = new ArrayList<>();
            BigDecimal totalAmount = BigDecimal.ZERO;

            for (OrderItemDTO itemDTO : orderDTO.getItems()) {
                Item item = itemRepository.findById(itemDTO.getItemId())
                        .orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemDTO.getItemId()));

                OrderItem orderItem = new OrderItem();
                orderItem.setItem(item);
                orderItem.setOrder(order);
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setPriceAtPurchase(item.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));

                totalAmount = totalAmount.add(orderItem.getPriceAtPurchase());
                newItems.add(orderItem);
            }

            order.setOrderItemList(newItems);
            order.setTotalAmount(totalAmount);
            List<OrderItem> orderItemList = orderItemRepository.saveAll(newItems);
        }

        Order savedOrder = orderRepository.save(order);
        result.put("success", true);
        result.put("data", savedOrder);
    }

    public void cancelOrder(Long orderId, Map<String, Object> result) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(com.example.demo.Enum.OrderStatus.CANCELLED);
        orderRepository.save(order);

        result.put("success", true);
        result.put("message", "Order cancelled successfully");
    }
}
