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

    int getHalfLife(String drugName){
        try {
            return new getHalfLifeAsyncTask(mDrugDao).execute(drugName).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // AsyncTask to delete an animal from the list
    private static class getHalfLifeAsyncTask extends AsyncTask<String, Void, Integer> {

        private DrugDao mDrugDao;

        getHalfLifeAsyncTask(DrugDao dao) {
            this.mDrugDao = dao;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return mDrugDao.getHalfLife(strings[0]);
        }
    }


}
