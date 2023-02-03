package com.rrsys.ordersapi.services;

import com.rrsys.ordersapi.models.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    Page<OrderEntity> getAllOrders(Pageable pageable);
    OrderEntity getOrder(UUID id);
    OrderEntity addOrder(OrderEntity entity);
    OrderEntity updateOrder(OrderEntity entity);
}
