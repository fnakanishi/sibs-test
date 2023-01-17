package org.example.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ItemRequest {
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}