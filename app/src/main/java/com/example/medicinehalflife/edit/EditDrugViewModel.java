package com.example.medicinehalflife.edit;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.medicinehalflife.data.Drug;

public class EditDrugViewModel extends AndroidViewModel {

    private EditDrugRepository mRepository;

    public EditDrugViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EditDrugRepository(application);
    }

    void editDrug(Drug drug){
        mRepository.editDrug(drug);
    }

}
