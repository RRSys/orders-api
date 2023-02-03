package com.rrsys.ordersapi.models;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemsEntity {
    private UUID id;
    private UUID productId;
    private OrderEntity order;
    private BigDecimal amount;
    private Integer quantity;
}
