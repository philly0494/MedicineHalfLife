package com.example.medicinehalflife;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DrugViewModel extends AndroidViewModel {

    private DrugRepository mRepository;
    private LiveData<List<Drug>> mAllDrugs;

    public DrugViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DrugRepository(application);
        mAllDrugs = mRepository.getAllDrugs();
    }

    LiveData<List<Drug>> getAllDrugs(){
        return mAllDrugs;
    }

}
