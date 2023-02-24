package com.rrsys.ordersapi.dtos;

import com.rrsys.ordersapi.enums.OrderStatusEnum;

public class OrderStatusDTO {

    private OrderStatusEnum status;

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderStatusDTO{" +
                "status=" + status +
                '}';
    }
}
