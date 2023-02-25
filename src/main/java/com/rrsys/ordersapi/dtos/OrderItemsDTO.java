package com.rrsys.ordersapi.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemsDTO {

    private UUID id;
    @NotNull
    private UUID productId;
    @NotNull
    private BigDecimal amount;
    @Positive
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
