package com.example.medicinehalflife;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DrugDao {

    @Insert
    void insert(Drug drug);

    @Query("SELECT * FROM drug_list ORDER BY name ASC")
    LiveData<List<Drug>> getAllDrugs();

    @Query("SELECT * FROM drug_list LIMIT 1")
    Drug[] getAnyDrug();

}
