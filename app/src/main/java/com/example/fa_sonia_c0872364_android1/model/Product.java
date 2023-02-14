package com.example.fa_sonia_c0872364_android1.model;

import androidx.annotation.NonNull;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

//@Entity(tableName = "products")
public class Product {
//    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name = "";

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
}
