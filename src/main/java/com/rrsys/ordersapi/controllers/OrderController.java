package com.rrsys.ordersapi.controllers;

import com.rrsys.ordersapi.dtos.OrderCreateDTO;
import com.rrsys.ordersapi.dtos.OrderUpdateDTO;
import com.rrsys.ordersapi.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    public static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderCreateDTO> create(@RequestBody @Valid OrderCreateDTO orderCreateDto) {
        log.info("creating a order: {}", orderCreateDto.toString());

        orderCreateDto = OrderCreateDTO.mapperToDto(orderService.create(orderCreateDto.mapperToEntity()));

        log.info("created order success: {}", orderCreateDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(orderCreateDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(orderCreateDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderCreateDTO> get(@PathVariable UUID id) {
        log.info("get order by id {}", id);

        OrderCreateDTO order = OrderCreateDTO.mapperToDto(orderService.get(id));

        log.info("retrieve order: {}", order);

        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<Page<OrderCreateDTO>> getAll(@PageableDefault(size = 20) Pageable pageable) {
        log.info("get all orders with parameters page:{}", pageable);

        Page<OrderCreateDTO> pageDto = orderService.getAll(pageable).map(OrderCreateDTO::mapperToDto);

        log.info("retrieve orders {}", pageDto.getContent());

        
        return ResponseEntity.ok(pageDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid OrderUpdateDTO orderUpdateDTO) {
        log.info("change status order:{}, status:{}", id, orderUpdateDTO);

        orderService.update(id, orderUpdateDTO.mapperToEntity());

        log.info("changed status order:{}, status{}", id, orderUpdateDTO);
        return ResponseEntity.noContent().build();
    }
}
