package com.example.fa_sonia_c0872364_android1.model;


import androidx.lifecycle.LiveData;

import com.example.fa_sonia_c0872364_android1.db.DbDao;

import java.util.List;


public class MainRepo {

    DbDao dbDao;
    public MainRepo(DbDao dbDao) {
        this.dbDao = dbDao;
    }


    public LiveData<List<Product>> getAllProducts(){
        return dbDao.getAllProducts();
    }
}
