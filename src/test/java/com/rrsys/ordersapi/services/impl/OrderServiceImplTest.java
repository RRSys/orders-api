package com.rrsys.ordersapi.services.impl;

import com.rrsys.ordersapi.enums.OrderStatusEnum;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

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

}