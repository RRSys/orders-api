package com.rrsys.ordersapi.dtos;

import com.rrsys.ordersapi.enums.OrderStatusEnum;
import com.rrsys.ordersapi.models.OrderEntity;
import com.rrsys.ordersapi.models.OrderItemsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {

    private UUID id;
    @NotNull
    private BigDecimal totalAmout;
    private LocalDateTime date;
    @NotBlank
    private String customerCPF;
    private OrderStatusEnum status;

    @Valid
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

    public static OrderCreateDTO mapperToDto(OrderEntity entity) {
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        BeanUtils.copyProperties(entity, orderCreateDTO);
        orderCreateDTO.setItems(new ArrayList<>());
        entity.getOrderItems().forEach(p-> {
            OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
            BeanUtils.copyProperties(p, orderItemsDTO);
            orderCreateDTO.addItem(orderItemsDTO);
        });
        return orderCreateDTO;
    }

    @Override
    public String toString() {
        return "OrderCreateDTO{" +
                "id=" + id +
                ", totalAmout=" + totalAmout +
                ", date=" + date +
                ", customerCPF='" + customerCPF + '\'' +
                ", status=" + status +
                ", items=" + items +
                '}';
    }
}
