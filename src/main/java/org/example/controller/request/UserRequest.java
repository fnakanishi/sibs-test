package org.example.controller.request;

import javax.validation.constraints.Size;

public class UserRequest {
    @Size(min = 3, max = 100)
    private String name;

    @Size(min = 5, max = 100)
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}