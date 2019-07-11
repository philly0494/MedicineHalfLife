package com.example.medicinehalflife.recycler;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.example.medicinehalflife.data.Drug;

import java.util.List;

public class RecyclerViewModel extends AndroidViewModel {

    private RecyclerRepository mRepository;
    private final LiveData<PagedList<Drug>> drugList;

    public RecyclerViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecyclerRepository(application);
        drugList = new LivePagedListBuilder<>(
                mRepository.getAllDrugsByName(), /*page size */ 10).build();
    }

    LiveData<PagedList<Drug>> getAllDrugsPagedList() {
        return drugList;
    }

    void deleteADrug(Drug drug) {
        mRepository.deleteADrug(drug);
    }

}
