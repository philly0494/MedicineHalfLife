package com.danapplabs.medicinehalflife.graph;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.danapplabs.medicinehalflife.data.Drug;

import java.util.List;

public class GraphViewModel extends AndroidViewModel {

    private GraphRepository mRepository;
    private LiveData<List<Drug>> mAllDrugs;

    public GraphViewModel(@NonNull Application application) {
        super(application);
        mRepository = new GraphRepository(application);
        mAllDrugs = mRepository.getAllDrugs();
    }

    LiveData<List<Drug>> getAllDrugs() {
        return mAllDrugs;
    }

    Drug getDrugByName(String drugName){
     return mRepository.getDrugByName(drugName);
    }

}
