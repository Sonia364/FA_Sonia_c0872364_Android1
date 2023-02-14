package com.example.fa_sonia_c0872364_android1.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fa_sonia_c0872364_android1.db.ProductRepository;

import java.util.List;

public class MainModelView extends AndroidViewModel {

    private ProductRepository repository;
    private final LiveData<List<Product>> allProducts;

    public MainModelView(@NonNull Application application) {
        super(application);

        repository = new ProductRepository(application);
        allProducts = repository.getAllEmployees();
    }

    public LiveData<Product> getProducts(int id){
        return repository.getProduct(id);
    }
    public LiveData<List<Product>> getAllProducts(String keyword){
        return repository.getAllProducts(keyword);
    }

    public void addProduct(Product product){
        repository.addProduct(product);
    }
    public void updateProduct(Product product){
        repository.updateProduct(product);
    }
    public void deleteProduct(Product product){
        repository.deleteProduct(product);
    }
}
