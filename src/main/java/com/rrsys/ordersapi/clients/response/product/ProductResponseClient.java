package com.rrsys.ordersapi.clients.response.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseClient {
    private UUID id;
    private String name;
    private String description;
    private double amount;
}
