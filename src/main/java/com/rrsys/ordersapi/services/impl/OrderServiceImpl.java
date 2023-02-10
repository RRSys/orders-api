package com.rrsys.ordersapi.services.impl;

import com.rrsys.ordersapi.enums.OrderStatusEnum;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.repositories.OrderRepository;
import com.rrsys.ordersapi.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

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

        entity.setDate(LocalDateTime.now());
        entity.setStatus(OrderStatusEnum.PENDING);

        return orderRepository.save(entity);
    }

    @Override
    public OrderEntity update(OrderEntity entity) {
        return null;
    }
}