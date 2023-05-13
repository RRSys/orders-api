package com.rrsys.ordersapi.services.impl;

import com.rrsys.ordersapi.clients.ProductsClients;
import com.rrsys.ordersapi.clients.response.product.ProductResponseClient;
import com.rrsys.ordersapi.enums.OrderStatusEnum;
import com.rrsys.ordersapi.exceptions.NotFoundException;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.models.OrderItemsEntity;
import com.rrsys.ordersapi.repositories.OrderRepository;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductsClients productsClients;

    @Test
    void shouldUpdateOrderEntityToAPPROVED() {
        OrderEntity orderEntityMockDb = new OrderEntity(OrderStatusEnum.PENDING);

        when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(orderEntityMockDb));

        OrderEntity result = orderService.update(UUID.randomUUID(), new OrderEntity(OrderStatusEnum.APPROVED));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getStatus(), OrderStatusEnum.APPROVED);
    }

    @Test
    void shouldUpdateOrderEntityToSameStatus() {
        OrderEntity orderEntityMockDb = new OrderEntity(OrderStatusEnum.PENDING);

        when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(orderEntityMockDb));

        OrderEntity result = orderService.update(UUID.randomUUID(), new OrderEntity(OrderStatusEnum.PENDING));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getStatus(), OrderStatusEnum.PENDING);
    }

    @Test
    void shouldUpdateOrderEntityToCOMPLETED() {
        OrderEntity orderEntityMockDb = new OrderEntity(OrderStatusEnum.APPROVED);

        when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(orderEntityMockDb));

        OrderEntity result = orderService.update(UUID.randomUUID(), new OrderEntity(OrderStatusEnum.COMPLETED));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getStatus(), OrderStatusEnum.COMPLETED);
    }

    @Test
    void shouldUpdateOrderEntityToCANCELLED() {
        OrderEntity orderEntityMockDb = new OrderEntity(OrderStatusEnum.PENDING);

        when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(orderEntityMockDb));

        OrderEntity result = orderService.update(UUID.randomUUID(), new OrderEntity(OrderStatusEnum.CANCELLED));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getStatus(), OrderStatusEnum.CANCELLED);
    }

    @Test
    void shouldLaunchAnExceptionWhenItChangesStatusNotAllowed() {
        OrderEntity orderEntityMockDb = new OrderEntity(OrderStatusEnum.PENDING);

        when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(orderEntityMockDb));

        Exception exception = assertThrows(RuntimeException.class, ()-> {
            orderService.update(UUID.randomUUID(), new OrderEntity(OrderStatusEnum.COMPLETED));
        });
        assertTrue(exception.getMessage().contains("status is not valid"));
    }

    @Test
    void shouldLaunchAnExceptionWhenItChangesStatusApprovedToPending() {
        OrderEntity orderEntityMockDb = new OrderEntity(OrderStatusEnum.APPROVED);

        when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(orderEntityMockDb));

        Exception exception = assertThrows(RuntimeException.class, ()-> {
            orderService.update(UUID.randomUUID(), new OrderEntity(OrderStatusEnum.PENDING));
        });
        assertTrue(exception.getMessage().contains("status is not valid"));
    }

    @Test
    void shouldLaunchAnExceptionWhenItChangesStatusPendingToCompleted() {
        OrderEntity orderEntityMockDb = new OrderEntity(OrderStatusEnum.PENDING);

        when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(orderEntityMockDb));

        Exception exception = assertThrows(RuntimeException.class, ()-> {
            orderService.update(UUID.randomUUID(), new OrderEntity(OrderStatusEnum.COMPLETED));
        });
        assertTrue(exception.getMessage().contains("status is not valid"));
    }

    @Test
    void shouldLaunchAnExceptionWhenItChangesStatusCompletedToCancelled() {
        OrderEntity orderEntityMockDb = new OrderEntity(OrderStatusEnum.COMPLETED);

        when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(orderEntityMockDb));

        Exception exception = assertThrows(RuntimeException.class, ()-> {
            orderService.update(UUID.randomUUID(), new OrderEntity(OrderStatusEnum.CANCELLED));
        });
        assertTrue(exception.getMessage().contains("status is not valid"));
    }

    @Test
    void shouldCreateANewOrderWithSuccess() {
        OrderEntity orderEntityMockDb = new OrderEntity(OrderStatusEnum.COMPLETED);
        when(orderRepository.save(any())).thenReturn(orderEntityMockDb);
        orderEntityMockDb = orderService.create(orderEntityMockDb);
        assertEquals(OrderStatusEnum.PENDING, orderEntityMockDb.getStatus());
        verify(orderRepository, times(1)).save(any());
    }

//    @Test
//    void shouldCreateANewOrderWithSuccessAndCalculateTotalAmout() {
//        var prod1 = UUID.randomUUID();
//        var prod2 = UUID.randomUUID();
//        OrderEntity orderEntityMockDb = getOrderEntity(List.of(prod1, prod2));
//
//        when(productsClients.findById(prod1))
//                .thenReturn(ResponseEntity.ok(getProductResponseClient(BigDecimal.valueOf(1.1))));
//        when(productsClients.findById(prod2))
//                .thenReturn(ResponseEntity.ok(getProductResponseClient(BigDecimal.valueOf(2.2))));
//        when(orderRepository.save(any())).thenReturn(orderEntityMockDb);
//        orderEntityMockDb = orderService.create(orderEntityMockDb);
//        assertEquals(OrderStatusEnum.PENDING, orderEntityMockDb.getStatus());
//        assertEquals(3.3D, orderEntityMockDb.getTotalAmount().doubleValue());
//        verify(orderRepository, times(1)).save(any());
//        verify(productsClients, atLeastOnce()).findById(any());
//    }

    @Test
    void shouldLaunchAnExceptionNotFoundExceptionWhenProductNotFound() {
        var prod1 = UUID.randomUUID();
        var prod2 = UUID.randomUUID();
        OrderEntity orderEntityMockDb = getOrderEntity(List.of(prod1, prod2));
        var mockException = mock(FeignException.class);

        when(productsClients.findById(prod1))
                .thenReturn(ResponseEntity.ok(getProductResponseClient(BigDecimal.valueOf(1.1))));
        when(productsClients.findById(prod2)).thenThrow(mockException);

        when(mockException.status()).thenReturn(404);

        NotFoundException exception = assertThrows(NotFoundException.class, ()-> {
            orderService.create(orderEntityMockDb);
        });
        assertTrue(exception.getMessage().contains("product not found, productId: "+prod2));
    }

    @Test
    void shouldCreateANewOrderWithSuccessAndCalculateTotalAmout() {
        var prod1 = UUID.randomUUID();
        var prod2 = UUID.randomUUID();
        OrderEntity orderEntityMockDb = getOrderEntity(List.of(prod1, prod2));

        when(productsClients.findById(prod1))
                .thenReturn(ResponseEntity.ok(getProductResponseClient(BigDecimal.valueOf(1.1))));
        when(productsClients.findById(prod2))
                .thenReturn(ResponseEntity.ok(getProductResponseClient(BigDecimal.valueOf(2.2))));
        when(orderRepository.save(any())).thenReturn(orderEntityMockDb);
        orderEntityMockDb = orderService.create(orderEntityMockDb);
        assertEquals(OrderStatusEnum.PENDING, orderEntityMockDb.getStatus());
        assertEquals(3.3D, orderEntityMockDb.getTotalAmount().doubleValue());
        verify(orderRepository, times(1)).save(any());
        verify(productsClients, atLeastOnce()).findById(any());
    }

    @Test
    void shouldGetAOrderWithSuccess() {
        var orderMock = new OrderEntity(OrderStatusEnum.APPROVED);
        when(orderRepository.findById(any())).thenReturn(Optional.of(orderMock));
        var order = orderService.get(UUID.randomUUID());
        assertNotNull(order);
        assertEquals(OrderStatusEnum.APPROVED, order.getStatus());
    }

    private OrderEntity getOrderEntity(List<UUID> productsId){
        OrderEntity orderEntityMockDb = new OrderEntity();
        List<OrderItemsEntity> orderItems = new ArrayList<>();
        productsId.forEach(p-> orderItems.add(getOrderItemsEntity(orderEntityMockDb, p)));
        orderEntityMockDb.setOrderItems(orderItems);
        return orderEntityMockDb;
    }

    private OrderItemsEntity getOrderItemsEntity(OrderEntity order, UUID productId) {
        OrderItemsEntity orderItemsEntity = new OrderItemsEntity();
        orderItemsEntity.setProductId(productId);
        orderItemsEntity.setOrder(order);
        orderItemsEntity.setAmount(BigDecimal.ONE);
        orderItemsEntity.setQuantity(1);
        return orderItemsEntity;
    }

    protected ProductResponseClient getProductResponseClient(BigDecimal amount) {
        return ProductResponseClient.builder()
                .amount(amount)
                .name("teste1")
                .build();
    }

}