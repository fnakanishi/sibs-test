package org.example.controller.request;

import javax.validation.constraints.NotNull;

public class StockMovementRequest {
    @NotNull
    private Long itemId;

    @NotNull
    private int quantity;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}