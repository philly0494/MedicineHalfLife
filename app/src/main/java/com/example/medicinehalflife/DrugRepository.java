package com.example.medicinehalflife;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DrugRepository {

    private DrugDao mDrugDao;
    private LiveData<List<Drug>> mAllDrug;

    DrugRepository(Application application){
        DrugRoomDatabase db = DrugRoomDatabase.getDatabase(application);
        mDrugDao = db.drugDao();
        mAllDrug = mDrugDao.getAllDrugs();
    }

    LiveData<List<Drug>> getAllDrugs(){
        return mAllDrug;
    }


}
