package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemSearchDto {
    private String name;

    private List<@PositiveOrZero(message = "Category ID must be positive") Long> categoryIds;

    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum price must be >= 0")
    private BigDecimal minPrice;

    @DecimalMin(value = "0.0", inclusive = true, message = "Maximum price must be >= 0")
    private BigDecimal maxPrice;

    private Boolean inStock;
}
