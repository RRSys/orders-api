package com.rrsys.ordersapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrsys.ordersapi.dtos.OrderCreateDTO;
import com.rrsys.ordersapi.dtos.OrderItemsDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGet() throws Exception {
        mockMvc.perform(get("/v1/orders"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void shouldCreateAOrder() {
        this.mockMvc
                .perform(post("/v1/orders")
                        .content(asJsonString(getOrderDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerCPF").exists());
    }

    public OrderCreateDTO getOrderDto(){
        return OrderCreateDTO.builder()
                .customerCPF("111111111")
                .totalAmout(BigDecimal.TEN)
                .items(Collections.singletonList(OrderItemsDTO.builder()
                        .amount(BigDecimal.TEN)
                        .productId(UUID.randomUUID())
                        .quantity(1)
                        .build()))
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}