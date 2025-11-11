package com.example.demo.controller;

import com.example.demo.dto.ItemDTO;
import com.example.demo.dto.ItemSearchDto;
import com.example.demo.dto.ItemUpdateDTO;
import com.example.demo.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Items", description = "CRUD operations for items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    @Operation(summary = "Get all items", description = "Retrieve all items with optional search and filter")
    public ResponseEntity getAllItems(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) List<Long> categoryIds,
                                      @RequestParam(required = false) BigDecimal minPrice,
                                      @RequestParam(required = false) BigDecimal maxPrice,
                                      @RequestParam(required = false) Boolean inStock) {
        Map<String, Object> result = new HashMap<>();
        ItemSearchDto searchDTO = new ItemSearchDto();
        searchDTO.setName(name);
        searchDTO.setCategoryIds(categoryIds);
        searchDTO.setMinPrice(minPrice);
        searchDTO.setMaxPrice(maxPrice);
        searchDTO.setInStock(inStock);
        itemService.getAllItems(searchDTO, result);
        return result.containsKey("success") ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "Get item by ID", description = "Retrieve a specific item by its ID")
    public ResponseEntity getItemById(@PathVariable Long itemId) {
        Map<String, Object> result = new HashMap<>();
        itemService.getItemById(itemId, result);
        return result.containsKey("success") ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @Operation(summary = "Create item", description = "Add a new item to the catalog")
    public ResponseEntity createItem(@Valid @RequestBody ItemDTO itemDTO) {
        Map<String, Object> result = new HashMap<>();
        itemService.createItem(itemDTO, result);
        return result.containsKey("success") ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "Update item", description = "Update an existing item by ID")
    public ResponseEntity updateItem(@PathVariable Long itemId, @Valid @RequestBody ItemUpdateDTO itemDTO) {
        Map<String, Object> result = new HashMap<>();
        itemService.updateItem(itemId, itemDTO, result);
        return result.containsKey("success") ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Delete item", description = "Delete an item by ID")
    public ResponseEntity deleteItem(@PathVariable Long itemId) {
        Map<String, Object> result = new HashMap<>();
        itemService.deleteItem(itemId, result);
        return result.containsKey("success") ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
