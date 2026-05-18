package com.dailycodework.dreamshop.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.*;

import lombok.Data;

@Data
public class OrderDto {

    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> items;
}