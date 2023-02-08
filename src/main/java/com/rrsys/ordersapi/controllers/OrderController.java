package com.rrsys.ordersapi.controllers;

import com.rrsys.ordersapi.dtos.OrderDTO;
import com.rrsys.ordersapi.enums.OrderStatusEnum;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.models.OrderItemsEntity;
import com.rrsys.ordersapi.services.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDto) {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerCPF(orderDto.getCustomerCPF());
        orderEntity.setTotalAmout(orderDto.getTotalAmout());
        orderEntity.setDate(LocalDateTime.now());
        orderEntity.setStatus(OrderStatusEnum.PENDING);
        orderDto.getOrderItems()
                .forEach(p-> {
                    OrderItemsEntity item = new OrderItemsEntity();
                    item.setProductId(p.getProductId());
                    item.setQuantity(p.getQuantity());
                    item.setAmount(p.getAmount());
                    item.setOrder(orderEntity);
                    orderEntity.addOrderItem(item);
                });

        orderService.create(orderEntity);
    }
}
