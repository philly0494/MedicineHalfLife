package com.example.medicinehalflife.add;

import android.app.Application;
import android.os.AsyncTask;
import com.example.medicinehalflife.data.Drug;
import com.example.medicinehalflife.data.DrugDao;
import com.example.medicinehalflife.data.DrugRoomDatabase;

import java.util.concurrent.ExecutionException;

public class AddDrugRepository {

    private DrugDao mDrugDao;

    AddDrugRepository(Application application){
        DrugRoomDatabase db = DrugRoomDatabase.getDatabase(application);
        mDrugDao = db.drugDao();
    }

    void addDrug(Drug drug){
        new addDrugAsyncTask(mDrugDao).execute(drug);
    }

    private static class addDrugAsyncTask extends AsyncTask<Drug,Void,Void>{

        private DrugDao mDrugDao;

        addDrugAsyncTask(DrugDao dao){
            this.mDrugDao = dao;
        }

        @Override
        protected Void doInBackground(Drug... drugs) {
            mDrugDao.insert(drugs[0]);
            return null;
        }
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
