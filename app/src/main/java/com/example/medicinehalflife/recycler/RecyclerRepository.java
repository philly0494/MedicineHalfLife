package com.example.medicinehalflife.recycler;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.medicinehalflife.data.Drug;
import com.example.medicinehalflife.data.DrugDao;
import com.example.medicinehalflife.data.DrugRoomDatabase;

import java.util.List;

public class RecyclerRepository {

    private DrugDao mDrugDao;
    private LiveData<List<Drug>> mAllDrug;

    RecyclerRepository(Application application){
        DrugRoomDatabase db = DrugRoomDatabase.getDatabase(application);
        mDrugDao = db.drugDao();
        mAllDrug = mDrugDao.getAllDrugs();
    }

    LiveData<List<Drug>> getAllDrugs(){
        return mAllDrug;
    }


}
