package com.rrsys.ordersapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrsys.ordersapi.dtos.OrderCreateDTO;
import com.rrsys.ordersapi.dtos.OrderItemsDTO;
import com.rrsys.ordersapi.dtos.OrderUpdateDTO;
import com.rrsys.ordersapi.enums.OrderStatusEnum;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.models.OrderItemsEntity;
import com.rrsys.ordersapi.services.OrderService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void shouldGetAOrderById() throws Exception {
        OrderEntity orderEntity = getOrder();
        when(orderService.get(any())).thenReturn(orderEntity);
        mockMvc.perform(get("/v1/orders/"+UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerCPF").value("111111111"))
                .andExpect(jsonPath("$.totalAmount").value(BigDecimal.TEN));
    }

    @SneakyThrows
    @Test
    public void shouldCreateAOrder() {
        when(orderService.create(any())).thenReturn(getOrder());
        this.mockMvc
                .perform(post("/v1/orders")
                        .content(asJsonString(getOrderDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerCPF").exists())
                .andExpect(jsonPath("$.totalAmount").value(BigDecimal.TEN));
    }

    @SneakyThrows
    @Test
    public void shouldUpdateOrder() {
        this.mockMvc
                .perform(put("/v1/orders/"+UUID.randomUUID())
                         .content(asJsonString(getOrderUpdateDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    public OrderCreateDTO getOrderDto(){
        return OrderCreateDTO.builder()
                .customerCPF("111111111")
                .totalAmount(BigDecimal.TEN)
                .items(Collections.singletonList(OrderItemsDTO.builder()
                        .amount(BigDecimal.TEN)
                        .productId(UUID.randomUUID())
                        .quantity(1)
                        .build()))
                .build();
    }

    public OrderUpdateDTO getOrderUpdateDto(){
        return OrderUpdateDTO.builder()
                    .id(UUID.randomUUID())
                    .status(OrderStatusEnum.APPROVED)
                .build();
    }

    public OrderEntity getOrder(){
        OrderEntity order = new OrderEntity();
        order.setId(UUID.randomUUID());
        order.setStatus(OrderStatusEnum.PENDING);
        order.setDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.TEN);
        order.setCustomerCPF("111111111");
        order.setOrderItems(Collections.singletonList(new OrderItemsEntity()));
        return order;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}