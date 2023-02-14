package com.example.fa_sonia_c0872364_android1.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fa_sonia_c0872364_android1.model.Product;

import java.util.List;

@Dao
public interface DbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategory(Product category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProduct(Product product);
    @Update
    void update(Product product);
    @Delete
    void delete(Product product);
    @Delete
    void deleteProduct(Product category);

    @Query("UPDATE products SET name =:productName WHERE id = :id")
    void updateProductName(String productName,int id);

    @Query("SELECT * FROM products ORDER BY name ASC")
    public abstract LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products WHERE id == :id")
    public abstract LiveData<Product> getProduct(int id);
    @Query("SELECT * FROM products WHERE name LIKE '%' || :keyword || '%' OR description LIKE '%' || :keyword || '%'")
    public abstract LiveData<List<Product>> getAllProducts(String keyword);
}
