package com.rrsys.ordersapi.controllers;

import com.rrsys.ordersapi.dtos.OrderDTO;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDto) {
        log.info("creating a order: {}", orderDto.toString());

        orderDto = OrderDTO.mapperToDto(orderService.create(orderDto.mapperToEntity()));

        log.info("created order success: {}", orderDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(orderDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(orderDto);
    }
}
