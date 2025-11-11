package com.example.demo.dto;

import com.example.demo.Enum.ItemUnit;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
public class ItemDTO {
    @NotBlank(message = "Item name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity must be greater than or equal to 0")
    private Integer stockQuantity;

    @NotNull(message = "Unit is required")
    private ItemUnit unit;

    @NotEmpty(message = "At least one category must be selected")
    private Set<Long> categoryIds; // IDs of categories assigned to this item
}
