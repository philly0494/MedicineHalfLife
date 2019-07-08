package com.example.medicinehalflife.recycler;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.medicinehalflife.data.Drug;
import com.example.medicinehalflife.graph.DrugRepository;

import java.util.List;

public class RecyclerViewModel extends AndroidViewModel {

    private RecyclerRepository mRepository;
    private LiveData<List<Drug>> mAllDrugs;

    public RecyclerViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecyclerRepository(application);
        mAllDrugs = mRepository.getAllDrugs();
    }

    LiveData<List<Drug>> getAllDrugs(){
        return mAllDrugs;
    }

}
