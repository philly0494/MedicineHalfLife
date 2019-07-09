package com.example.medicinehalflife.graph;

import android.app.Application;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.medicinehalflife.data.Drug;
import com.example.medicinehalflife.data.DrugDao;
import com.example.medicinehalflife.data.DrugRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GraphRepository {

    private DrugDao mDrugDao;
    private LiveData<List<Drug>> mAllDrug;

    GraphRepository(Application application){
        DrugRoomDatabase db = DrugRoomDatabase.getDatabase(application);
        mDrugDao = db.drugDao();
        mAllDrug = mDrugDao.getAllDrugs();
    }

    LiveData<List<Drug>> getAllDrugs(){
        return mAllDrug;
    }

    Drug getDrugByName(String drugName){
        try {
            return new getDrugByNameAsyncTask(mDrugDao).execute(drugName).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    // AsyncTask to get a Drug Object by it's name
    private static class getDrugByNameAsyncTask extends AsyncTask<String, Void, Drug> {

        private DrugDao mDrugDao;

        getDrugByNameAsyncTask(DrugDao dao) {
            this.mDrugDao = dao;
        }

        @Override
        protected Drug doInBackground(String... names) {
            return mDrugDao.getDrugByName(names[0]);
        }
    }


}
