package com.cwa.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {
    private String query;
    private String category;
    private Integer limit = 30;
    private Integer skip = 0;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
