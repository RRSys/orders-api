package com.rrsys.ordersapi.models;

import java.util.UUID;

public class ProductEntity {
    private UUID productId;
    private String name;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
