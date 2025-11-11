package com.example.demo.dto;

import com.example.demo.Enum.ItemUnit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ItemUpdateDTO {
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal price;

    @Min(value = 0, message = "Stock quantity cannot be negative.")
    private Integer stockQuantity;

    private ItemUnit unit;

    private Set<Long> categoryIds;
}
