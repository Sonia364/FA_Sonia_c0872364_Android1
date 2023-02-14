package com.example.fa_sonia_c0872364_android1.db;
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fa_sonia_c0872364_android1.model.Product;

import java.util.List;

public class ProductRepository {

    private DbDao dbDao;

    private LiveData<List<Product>> allProducts;

    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        dbDao = db.dbDao();
        allProducts = dbDao.getAllProducts();

    }

    public LiveData<Product> getProduct(int id){
        return dbDao.getProduct(id);
    }
    public LiveData<List<Product>> getAllProducts(String keyword){
        return dbDao.getAllProducts(keyword);
    }
    public void addProduct(Product product){
        AppDatabase.databaseExecutor.execute( () -> {
            dbDao.addProduct(product);
        });
    }
    public void updateProduct(Product product){
        AppDatabase.databaseExecutor.execute( () -> {
            dbDao.update(product);
        });
    }
    public void deleteProduct(Product product){
        AppDatabase.databaseExecutor.execute( () -> {
            dbDao.delete(product);
        });
    }

    public LiveData<List<Product>> getAllEmployees() {
        return allProducts;
    }


}
