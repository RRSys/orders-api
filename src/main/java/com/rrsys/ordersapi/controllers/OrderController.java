package com.rrsys.ordersapi.controllers;

import com.rrsys.ordersapi.dtos.OrderDTO;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO orderDto) {
        log.info("creating a order: {}", orderDto.toString());
        OrderEntity orderEntity = orderDto.mapperToEntity();
        orderEntity = orderService.create(orderEntity);

        orderDto = OrderDTO.mapperToDto(orderEntity);

        log.info("created order success: {}", orderDto);

        return orderDto;
    }
}
