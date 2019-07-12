package com.danapplabs.medicinehalflife.recycler;

import android.app.Application;
import android.os.AsyncTask;

import androidx.paging.DataSource;
import com.danapplabs.medicinehalflife.data.Drug;
import com.danapplabs.medicinehalflife.data.DrugDao;
import com.danapplabs.medicinehalflife.data.DrugRoomDatabase;

import java.util.concurrent.ExecutionException;

public class RecyclerRepository {

    private DrugDao mDrugDao;

    RecyclerRepository(Application application) {
        DrugRoomDatabase db = DrugRoomDatabase.getDatabase(application);
        mDrugDao = db.drugDao();
    }

    DataSource.Factory<Integer, Drug> getAllDrugsByName() {
        try {
            return new getAllDrugsByNameAsyncTask(mDrugDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    void deleteADrug(Drug drug) {
        new deleteADrugAsyncTask(mDrugDao).execute(drug);
    }

    //AsyncTask to return a DataSource.Factory<Integer, Drug>, which is used to get a PagedList of all the drugs
    private static class getAllDrugsByNameAsyncTask extends AsyncTask<Void, Void, DataSource.Factory<Integer, Drug>> {

        private DrugDao mAsyncTaskDao;

        getAllDrugsByNameAsyncTask(DrugDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected DataSource.Factory<Integer, Drug> doInBackground(Void... voids) {
            return mAsyncTaskDao.getAllDrugsByName();
        }

    }

    // AsyncTask to delete a drug from the list
    private static class deleteADrugAsyncTask extends AsyncTask<Drug, Void, Void> {

        private DrugDao mAsyncTaskDao;

        deleteADrugAsyncTask(DrugDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Drug... drugs) {
            mAsyncTaskDao.deleteDrug(drugs[0]);
            return null;
        }

    }

}
