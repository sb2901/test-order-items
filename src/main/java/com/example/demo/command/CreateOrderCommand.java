package com.example.demo.command;

import java.util.List;

public class CreateOrderCommand {

    private Long clientId;
    private List<CreateItemsCommand> items;
    private Double totalPrice;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<CreateItemsCommand> getItems() {
        return items;
    }

    public void setItems(List<CreateItemsCommand> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
