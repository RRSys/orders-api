package com.rrsys.ordersapi.services.impl;

import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Page<OrderEntity> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public OrderEntity get(UUID id) {
        return null;
    }

    @Override
    public OrderEntity create(OrderEntity entity) {
        return null;
    }

    @Override
    public OrderEntity update(OrderEntity entity) {
        return null;
    }
}
