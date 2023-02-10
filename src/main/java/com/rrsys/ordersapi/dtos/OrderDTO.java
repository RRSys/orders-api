package com.rrsys.ordersapi.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDTO {

    private UUID id;
    private BigDecimal totalAmout;
    private String customerCPF;

    private List<OrderItemsDTO> items = new ArrayList<>();

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

}
