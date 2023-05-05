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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

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
    void shouldGetAOrderById() throws Exception {
        OrderEntity orderEntity = getOrder();
        when(orderService.get(any())).thenReturn(orderEntity);
        mockMvc.perform(get("/v1/orders/"+UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerCPF").value("111111111"))
                .andExpect(jsonPath("$.totalAmount").value(10))
                .andExpect(jsonPath("$.items", not(empty())))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].amount", is(10)))
                .andExpect(jsonPath("$.items[0].quantity", is(1)))
                .andExpect(jsonPath("$.items[0].productId", notNullValue()));
    }

    @SneakyThrows
    @Test
    void shouldCreateAOrder() {
        when(orderService.create(any())).thenReturn(getOrder());
        this.mockMvc
                .perform(post("/v1/orders")
                        .content(asJsonString(getOrderDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerCPF").value("111111111"))
                .andExpect(jsonPath("$.totalAmount").value(10))
                .andExpect(jsonPath("$.items", not(empty())))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].amount", is(10)))
                .andExpect(jsonPath("$.items[0].quantity", is(1)))
                .andExpect(jsonPath("$.items[0].productId", notNullValue()));
    }

    @SneakyThrows
    @Test
    void shouldUpdateOrder() {
        this.mockMvc
                .perform(put("/v1/orders/"+UUID.randomUUID())
                         .content(asJsonString(getOrderUpdateDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).update(any(), any());
    }

    OrderCreateDTO getOrderDto(){
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

    OrderUpdateDTO getOrderUpdateDto(){
        return OrderUpdateDTO.builder()
                    .id(UUID.randomUUID())
                    .status(OrderStatusEnum.APPROVED)
                .build();
    }

    OrderEntity getOrder(){
        OrderEntity order = new OrderEntity();
        order.setId(UUID.randomUUID());
        order.setStatus(OrderStatusEnum.PENDING);
        order.setDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.TEN);
        order.setCustomerCPF("111111111");
        OrderItemsEntity orderItemsEntity = new OrderItemsEntity();
        orderItemsEntity.setAmount(BigDecimal.TEN);
        orderItemsEntity.setQuantity(1);
        orderItemsEntity.setProductId(UUID.randomUUID());
        order.setOrderItems(Collections.singletonList(orderItemsEntity));
        return order;
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}