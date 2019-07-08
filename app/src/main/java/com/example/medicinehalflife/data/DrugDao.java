package com.example.medicinehalflife.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.medicinehalflife.data.Drug;

import java.util.List;

@Dao
public interface DrugDao {

    @Insert
    void insert(Drug drug);

    @Query("SELECT * FROM drug_list ORDER BY name ASC")
    LiveData<List<Drug>> getAllDrugs();

    @Query("SELECT * FROM drug_list LIMIT 1")
    Drug[] getAnyDrug();

    @Query("SELECT half_life FROM drug_list WHERE name=:drugName")
    int getHalfLife(String drugName);

}
