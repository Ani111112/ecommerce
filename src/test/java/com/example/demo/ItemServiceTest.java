package com.example.demo;

import com.example.demo.dto.ItemDTO;
import com.example.demo.entities.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void createItem_shouldReturnSuccess() {
        ItemDTO dto = new ItemDTO();
        dto.setName("Test Item");
        dto.setDescription("Desc");
        dto.setPrice(BigDecimal.valueOf(100));
        dto.setStockQuantity(10);
        dto.setCategoryIds(new HashSet<>()); // <- important!

        Item savedItem = new Item();
        savedItem.setId(1L);
        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

        Map<String, Object> result = new HashMap<>();
        itemService.createItem(dto, result);

        assertTrue(result.containsKey("success"));
        assertTrue(result.containsKey("data"));
    }

    @Test
    void getItemById_whenExists() {
        Item item = new Item();
        item.setId(1L);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Map<String, Object> result = new HashMap<>();
        itemService.getItemById(1L, result);

        assertTrue(result.containsKey("data"));
    }

    @Test
    void getItemById_whenNotExists() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        Map<String, Object> result = new HashMap<>();
        itemService.getItemById(1L, result);

        assertTrue(result.containsKey("error"));
        assertEquals("Item not found", result.get("error"));
    }

    @Test
    void deleteItem_shouldReturnSuccess() {
        Item item = new Item();
        item.setId(1L);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Map<String, Object> result = new HashMap<>();
        itemService.deleteItem(1L, result);

        assertTrue(result.containsKey("success"));
        assertEquals("Item deleted successfully", result.get("message"));
        verify(itemRepository, times(1)).delete(item);
    }
}
