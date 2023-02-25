package com.rrsys.ordersapi.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders_items")
public class OrderItemsEntity {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "varchar(255)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "varchar(255)", nullable = false, updatable = false)
    private UUID productId;

    //many items to one order
    @ManyToOne
    @JoinColumn(name= "order_id", nullable = false)
    private OrderEntity order;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private Integer quantity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
