package com.example.demo.specifications;

import com.example.demo.dto.ItemSearchDto;
import com.example.demo.entities.Category;
import com.example.demo.entities.Item;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecification implements Specification<Item> {
    private final ItemSearchDto itemSearchDto;

    public ItemSpecification(ItemSearchDto itemSearchDto) {
        this.itemSearchDto = itemSearchDto;
    }

    @Override
    public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isBlank(itemSearchDto.getName())){
            predicates.add(builder.like(builder.upper(root.get("name")), "%" + itemSearchDto.getName()+ "%"));
        }


        if (itemSearchDto.getMinPrice() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("price"), itemSearchDto.getMinPrice()));
        }
        if (itemSearchDto.getMaxPrice() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("price"), itemSearchDto.getMaxPrice()));
        }


        if (itemSearchDto.getInStock() != null && itemSearchDto.getInStock()) {
            predicates.add(builder.greaterThan(root.get("stockQuantity"), 0));
        }


        if (itemSearchDto.getCategoryIds() != null && !itemSearchDto.getCategoryIds().isEmpty()) {
            Join<Item, Category> categoryJoin = root.join("categoryList", JoinType.INNER);
            predicates.add(categoryJoin.get("id").in(itemSearchDto.getCategoryIds()));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
