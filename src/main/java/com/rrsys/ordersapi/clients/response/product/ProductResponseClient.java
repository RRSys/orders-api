package com.rrsys.ordersapi.clients.response.product;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseClient {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal amount;
}
