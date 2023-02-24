package com.rrsys.ordersapi.controllers;

import com.rrsys.ordersapi.dtos.OrderDTO;
import com.rrsys.ordersapi.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable UUID id) {
        log.info("get order by id {}", id);

        OrderDTO order = OrderDTO.mapperToDto(orderService.get(id));

        log.info("retrieve order: {}", order);

        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAll(@PageableDefault(size = 20) Pageable pageable) {
        log.info("get all orders with parameters page:{}", pageable);

        Page<OrderDTO> pageDto = orderService.getAll(pageable).map(OrderDTO::mapperToDto);

        log.info("retrieve orders {}", pageDto.getContent());

        
        return ResponseEntity.ok(pageDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody OrderDTO orderDto) {
        log.info("change status order:{}, status:{}", id, orderDto);

        orderService.update(orderDto.mapperToEntity());

        log.info("changed status order:{}, status{}", id, orderDto);
        return ResponseEntity.noContent().build();
    }
}
