package com.example.fa_sonia_c0872364_android1.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {
@PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name = "";

    private String description = "";

    private double price;
    private double providerLat;
    private double providerLng;
    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getProviderLat() {
        return providerLat;
    }

    public void setProviderLat(double providerLat) {
        this.providerLat = providerLat;
    }

    public double getProviderLng() {
        return providerLng;
    }

    public void setProviderLng(double providerLng) {
        this.providerLng = providerLng;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
