package com.danapplabs.medicinehalflife.edit;

import android.app.Application;
import android.os.AsyncTask;
import com.danapplabs.medicinehalflife.data.Drug;
import com.danapplabs.medicinehalflife.data.DrugDao;
import com.danapplabs.medicinehalflife.data.DrugRoomDatabase;

public class EditDrugRepository {

    private DrugDao mDrugDao;

    EditDrugRepository(Application application){
        DrugRoomDatabase db = DrugRoomDatabase.getDatabase(application);
        mDrugDao = db.drugDao();
    }

    void editDrug(Drug drug){
        new editDrugAsyncTask(mDrugDao).execute(drug);
    }

    private static class editDrugAsyncTask extends AsyncTask<Drug,Void,Void> {

        private DrugDao mDrugDao;

        editDrugAsyncTask(DrugDao dao){
            this.mDrugDao = dao;
        }

        @Override
        protected Void doInBackground(Drug... drugs) {
            mDrugDao.updateDrug(drugs[0]);
            return null;
        }
    }

}
