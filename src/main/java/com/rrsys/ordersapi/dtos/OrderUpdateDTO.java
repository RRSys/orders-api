package com.rrsys.ordersapi.dtos;

import com.rrsys.ordersapi.enums.OrderStatusEnum;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.models.OrderItemsEntity;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderUpdateDTO {

    private UUID id;
    @NotNull
    private OrderStatusEnum status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public OrderEntity mapperToEntity(){
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(this, orderEntity);
        return orderEntity;
    }

    public static OrderUpdateDTO mapperToDto(OrderEntity entity) {
        OrderUpdateDTO orderCreateDTO = new OrderUpdateDTO();
        BeanUtils.copyProperties(entity, orderCreateDTO);
        return orderCreateDTO;
    }

    @Override
    public String toString() {
        return "OrderUpdateDTO{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
