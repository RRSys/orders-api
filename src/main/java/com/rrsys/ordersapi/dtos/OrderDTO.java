package com.rrsys.ordersapi.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrsys.ordersapi.enums.OrderStatusEnum;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.models.OrderItemsEntity;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDTO {

    private UUID id;
    private BigDecimal totalAmout;
    private LocalDateTime date;
    private String customerCPF;
    private OrderStatusEnum status;

    private List<OrderItemsDTO> items = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(BigDecimal totalAmout) {
        this.totalAmout = totalAmout;
    }

    public String getCustomerCPF() {
        return customerCPF;
    }

    public void setCustomerCPF(String customerCPF) {
        this.customerCPF = customerCPF;
    }

    public List<OrderItemsDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemsDTO> items) {
        this.items = items;
    }

    public void addItem(OrderItemsDTO items) {
        this.getItems().add(items);
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
        this.items.forEach(p-> {
            OrderItemsEntity orderItem = new OrderItemsEntity();
            BeanUtils.copyProperties(p, orderItem);
            orderEntity.addOrderItem(orderItem);
        });
        return orderEntity;
    }

    public static OrderDTO mapperToDto(OrderEntity entity) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(entity, orderDTO);
        orderDTO.setItems(new ArrayList<>());
        entity.getOrderItems().forEach(p-> {
            OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
            BeanUtils.copyProperties(p, orderItemsDTO);
            orderDTO.addItem(orderItemsDTO);
        });
        return orderDTO;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", totalAmout=" + totalAmout +
                ", customerCPF='" + customerCPF + '\'' +
                ", items=" + items +
                '}';
    }

}
