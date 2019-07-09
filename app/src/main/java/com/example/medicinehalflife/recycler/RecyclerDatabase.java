package com.example.medicinehalflife.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.medicinehalflife.R;
import com.example.medicinehalflife.data.Drug;

import java.util.List;

public class RecyclerDatabase extends AppCompatActivity {

    private RecyclerViewModel mRecyclerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_database);

        final RecyclerAdapter adapter = new RecyclerAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerViewModel = ViewModelProviders.of(this).get(RecyclerViewModel.class);
        // Update the cached copy of the drugs in the adapter.
        mRecyclerViewModel.getAllDrugs().observe(this, adapter::setDrugs);

    }
}