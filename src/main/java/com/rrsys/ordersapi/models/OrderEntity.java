package com.rrsys.ordersapi.models;

import com.rrsys.ordersapi.enums.OrderStatusEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private BigDecimal totalAmout;
    private LocalDateTime date;
    private String customerCPF;
    private OrderStatusEnum status;

    //one orders to many items
    @OneToMany(mappedBy = "order")
    private List<OrderItemsEntity> orderItems = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(BigDecimal totalAmout) {
        this.totalAmout = totalAmout;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCustomerCPF() {
        return customerCPF;
    }

    public void setCustomerCPF(String customerCPF) {
        this.customerCPF = customerCPF;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public List<OrderItemsEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemsEntity> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItemsEntity orderItemsEntity) {
        orderItems.add(orderItemsEntity);
        orderItemsEntity.setOrder(this);
    }
}
