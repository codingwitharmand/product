package com.cwa.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String brand;
    private Double rating;
    private Integer stock;
    private String thumbnail;
    private List<String> images;
    private BigDecimal discountPercentage;
    private BigDecimal discountedPrice;
}
