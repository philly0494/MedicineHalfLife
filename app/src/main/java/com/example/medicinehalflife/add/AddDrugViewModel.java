package com.example.medicinehalflife.add;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.medicinehalflife.data.Drug;

public class AddDrugViewModel extends AndroidViewModel {

    private AddDrugRepository mRepository;

    public AddDrugViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AddDrugRepository(application);
    }

    void addDrug(Drug drug){
        mRepository.addDrug(drug);
    }

    Drug getDrugByName(String drugName){
        return mRepository.getDrugByName(drugName);
    }
}
