package com.rrsys.ordersapi.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemsDTO {

    private UUID id;
    private UUID productId;
    private BigDecimal amount;
    private Integer quantity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemsDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", amount=" + amount +
                ", quantity=" + quantity +
                '}';
    }
}
