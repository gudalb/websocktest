package com.example.websocktest;

import java.time.LocalDateTime;
import java.util.UUID;

public class Item {
    private String id;
    private long quantity;
    private String name;
    private LocalDateTime time;

    public Item(String id, long quantity, String name) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        time = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}
