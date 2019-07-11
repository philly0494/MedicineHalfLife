package com.example.medicinehalflife.data;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.*;

import java.util.List;

@Dao
public interface DrugDao {

    @Insert
    void insert(Drug drug);

    @Query("SELECT * FROM drug_list ORDER BY name ASC")
    LiveData<List<Drug>> getAllDrugs();

    @Query("SELECT * FROM drug_list ORDER BY name ASC")
    DataSource.Factory<Integer,Drug> getAllDrugsByName();

    @Query("SELECT * FROM drug_list LIMIT 1")
    Drug[] getAnyDrug();

    @Query("SELECT * FROM drug_list WHERE name=:drugName LIMIT 1")
    Drug getDrugByName(String drugName);

    @Update
    void updateDrug(Drug drug);

    @Delete
    void deleteDrug(Drug drug);

}
