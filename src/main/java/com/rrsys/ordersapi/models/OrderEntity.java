package com.rrsys.ordersapi.models;

import com.rrsys.ordersapi.enums.OrderStatusEnum;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "varchar(255)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private BigDecimal totalAmount;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false, updatable = false)
    private String customerCPF;
    @Column(nullable = false)
    private OrderStatusEnum status;

    //one orders to many items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemsEntity> orderItems = new ArrayList<>();

    public OrderEntity(OrderStatusEnum status) {
        this.status = status;
    }

    public OrderEntity(UUID id, BigDecimal totalAmount, LocalDateTime date, String customerCPF, OrderStatusEnum status, List<OrderItemsEntity> orderItems) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.date = date;
        this.customerCPF = customerCPF;
        this.status = status;
        this.orderItems = orderItems;
    }

    public OrderEntity() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
