package com.example.demo.service;

import com.example.demo.dto.ItemDTO;
import com.example.demo.dto.ItemSearchDto;
import com.example.demo.dto.ItemUpdateDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.specifications.ItemSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public void getAllItems(ItemSearchDto searchDTO, Map<String, Object> result) {
        ItemSpecification itemSpecification = new ItemSpecification(searchDTO);
        List<Item> itemList = itemRepository.findAll(itemSpecification);

        result.put("success", itemList);
    }

    public void updateItem(Long itemId, ItemUpdateDTO itemDTO, Map<String, Object> result) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item is not present"));

        if (itemDTO.getName() != null) item.setName(itemDTO.getName());
        if (itemDTO.getDescription() != null) item.setDescription(itemDTO.getDescription());
        if (itemDTO.getPrice() != null) item.setPrice(itemDTO.getPrice());
        if (itemDTO.getStockQuantity() != null) item.setStockQuantity(itemDTO.getStockQuantity());
        if (itemDTO.getUnit() != null) item.setUnit(itemDTO.getUnit());

        if (!CollectionUtils.isEmpty(itemDTO.getCategoryIds())) {
            Set<Category> categories = new HashSet<>();
            for (Long catId : itemDTO.getCategoryIds()) {
                categoryRepository.findById(catId).ifPresent(categories::add);
            }
            item.setCategoryList(new HashSet<>(categories));
        }

        Item savedItem = itemRepository.save(item);
        result.put("success", true);
        result.put("data", savedItem);
    }

    public void getItemById(Long itemId, Map<String, Object> result) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isPresent()) {
            result.put("success", true);
            result.put("data", item.get());
        } else {
            result.put("error", "Item not found");
        }
    }

    public void createItem(@Valid ItemDTO itemDTO, Map<String, Object> result) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setStockQuantity(itemDTO.getStockQuantity());
        item.setUnit(itemDTO.getUnit());

        Set<Category> categories = new HashSet<>();
        for (Long catId : itemDTO.getCategoryIds()) {
            categoryRepository.findById(catId).ifPresent(categories::add);
        }
        item.setCategoryList(new HashSet<>(categories));

        Item savedItem = itemRepository.save(item);
        result.put("success", true);
        result.put("data", savedItem);
    }

    public void deleteItem(Long itemId, Map<String, Object> result) {
        Optional<Item> itemOpt = itemRepository.findById(itemId);
        if (itemOpt.isPresent()) {
            itemRepository.delete(itemOpt.get());
            result.put("success", true);
            result.put("message", "Item deleted successfully");
        } else {
            result.put("error", "Item not found");
        }
    }
}
