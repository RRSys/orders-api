package com.rrsys.ordersapi.services;

import com.rrsys.ordersapi.models.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    Page<OrderEntity> getAll(Pageable pageable);
    OrderEntity get(UUID id);
    OrderEntity create(OrderEntity entity);
    OrderEntity update(UUID id, OrderEntity entity);
}
