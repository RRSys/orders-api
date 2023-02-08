package com.rrsys.ordersapi.controllers;

import com.rrsys.ordersapi.dtos.OrderDTO;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.models.OrderItemsEntity;
import com.rrsys.ordersapi.services.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

        List<OrderItemsEntity> list = orderDto.getItems().stream().map(p-> {
                    OrderItemsEntity item = new OrderItemsEntity();
                    item.setProductId(p.getProductId());
                    item.setQuantity(p.getQuantity());
                    item.setAmount(p.getAmount());
                    return item;
                }).collect(Collectors.toList());

        list.forEach(orderEntity::addOrderItem);

        orderEntity = orderService.create(orderEntity);

        orderDto.setId(orderEntity.getId());

        return orderDto;
    }
}
