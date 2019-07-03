package com.example.medicinehalflife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class DrugDatabase extends AppCompatActivity {

    private DrugViewModel mDrugViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_database);

        final DrugListAdapter adapter = new DrugListAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDrugViewModel = ViewModelProviders.of(this).get(DrugViewModel.class);
        mDrugViewModel.getAllDrugs().observe(this, new Observer<List<Drug>>() {
            @Override
            public void onChanged(List<Drug> drugs) {
                // Update the cached copy of the drugs in the adapter.
                adapter.setDrugs(drugs);
            }
        });

    }
}
