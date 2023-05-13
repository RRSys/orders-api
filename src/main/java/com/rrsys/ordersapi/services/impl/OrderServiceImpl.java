package com.rrsys.ordersapi.services.impl;

import com.rrsys.ordersapi.clients.ProductsClients;
import com.rrsys.ordersapi.enums.OrderStatusEnum;
import com.rrsys.ordersapi.exceptions.NotFoundException;
import com.rrsys.ordersapi.exceptions.ValidationOrderException;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.models.OrderItemsEntity;
import com.rrsys.ordersapi.repositories.OrderRepository;
import com.rrsys.ordersapi.services.OrderService;
import feign.FeignException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductsClients productsClients;

    public OrderServiceImpl(OrderRepository orderRepository, ProductsClients productsClients) {
        this.orderRepository = orderRepository;
        this.productsClients = productsClients;
    }

    @Override
    public Page<OrderEntity> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public OrderEntity get(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Order not found"));
    }

    @Override
    public OrderEntity create(OrderEntity entity) {

        entity.setDate(LocalDateTime.now());
        entity.setStatus(OrderStatusEnum.PENDING);

        entity.getOrderItems().forEach(this::processProduct);
        processOrder(entity);

        return orderRepository.save(entity);
    }

    private void processOrder(OrderEntity entity) {
        entity.setTotalAmount(
                entity.getOrderItems().stream()
                        .map(p-> p.getAmount().multiply(BigDecimal.valueOf(p.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    @Override
    public OrderEntity update(UUID id, OrderEntity entity) {

        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Order not found"));

        setAndValidOrderStatus(orderEntity, entity.getStatus());

        orderRepository.save(orderEntity);

        return orderEntity;
    }

    private void setAndValidOrderStatus(OrderEntity order, OrderStatusEnum status) {
        if(order.getStatus() == status) return;
        boolean validStatus = false;
        switch (status) {
            case APPROVED:
                validStatus = order.getStatus() == OrderStatusEnum.PENDING;
                break;
            case COMPLETED:
                validStatus = order.getStatus() == OrderStatusEnum.APPROVED;
                break;
            case CANCELLED:
                validStatus = order.getStatus() == OrderStatusEnum.APPROVED || order.getStatus() == OrderStatusEnum.PENDING;
                break;
            default:
        }

        if(!validStatus) {
           throw new ValidationOrderException("status is not valid");
        }
        order.setStatus(status);
    }

    private void processProduct(OrderItemsEntity orderItemsEntity){
        try {
            var product = productsClients.findById(orderItemsEntity.getProductId()).getBody();
            orderItemsEntity.setAmount(product.getAmount());
        } catch (FeignException ex) {
            if(ex.status() == 404) {
                throw new NotFoundException("product not found, productId: "+ orderItemsEntity.getProductId());
            }
            throw new ValidationOrderException("fatal error: "+ex.contentUTF8());
        }
    }
}
