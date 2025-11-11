package com.example.demo;

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
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void createOrder_shouldReturnSuccess() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Item item = new Item();
        item.setId(1L);
        item.setPrice(BigDecimal.valueOf(100));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Mock savedOrder
        Order savedOrder = new Order();
        savedOrder.setId(1L);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Mock savedOrderItem list
        OrderItem savedItem = new OrderItem();
        when(orderItemRepository.saveAll(anyList())).thenReturn(Collections.singletonList(savedItem));

        OrderDTO dto = new OrderDTO();
        dto.setUserId(1L);
        dto.setStatus("CREATED");
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setItemId(1L);
        itemDTO.setQuantity(2);
        dto.setItems(Collections.singletonList(itemDTO));

        Map<String, Object> result = new HashMap<>();
        orderService.createOrder(dto, result);

        assertTrue(result.containsKey("success"));
        assertEquals("Order created successfully", result.get("message"));
        assertEquals(1L, result.get("orderId"));
    }
}
