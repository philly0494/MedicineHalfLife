package com.example.medicinehalflife.recycler;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.medicinehalflife.R;
import com.example.medicinehalflife.add.AddDrugActivity;
import com.example.medicinehalflife.data.Drug;
import com.example.medicinehalflife.edit.EditDrugActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerDatabase extends AppCompatActivity {

    public static final String EDIT_DRUG_NAME = "drug";
    public static final String EDIT_DRUG_HALFLIFE = "halflife";
    public static final String EDIT_DRUG_HALFLIFE_UNIT = "unit";

    private RecyclerViewModel mRecyclerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_database);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PagerListAdapter adapter = new PagerListAdapter(this);

        //This method exposes the interface for the onClick Listener for the viewHolder
        adapter.setOnClickListener((v, position) -> {
            // Do something here for when user clicks on the viewholder item in the adapter.
        });

        //Build an alert for user to confirm their choices
        final AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(RecyclerDatabase.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerViewModel = ViewModelProviders.of(this).get(RecyclerViewModel.class);
        // Update the cached copy of the drugs in the adapter.
        mRecyclerViewModel.getAllDrugsPagedList().observe(this, adapter::submitList);

        //This method exposes the context menu listener interface.
        adapter.setOnContextMenuClickListener((item, adapterPosition) -> {
            int position = item.getItemId();
            if (position == PagerListAdapter.MENU_ID_DELETE) {
                myAlertBuilder.setTitle("Do you want to delete this entry?");
                myAlertBuilder.setMessage("Click OK to continue, or Cancel to stop:");
                myAlertBuilder.setPositiveButton("OK", (dialog, which) -> {
                    // User clicked OK button.
                    Toast.makeText(getApplicationContext(), "Pressed OK, and deleted that item", Toast.LENGTH_SHORT).show();
                    // get the animal from the adapter position
                    final Drug drug = adapter.getDrugAtPosition(adapterPosition);
                    // delete that animal using the view model.
                    mRecyclerViewModel.deleteADrug(drug);
                });
                myAlertBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                    // User cancelled the dialog.
                    Toast.makeText(getApplicationContext(), "Pressed Cancel", Toast.LENGTH_SHORT).show();
                });
                // Create and show the AlertDialog.
                myAlertBuilder.show();
            }
            if (position == PagerListAdapter.MENU_ID_EDIT){
                Intent intent = new Intent(this, EditDrugActivity.class);
                final Drug drug = adapter.getDrugAtPosition(adapterPosition);
                intent.putExtra(EDIT_DRUG_NAME,drug.getName());
                intent.putExtra(EDIT_DRUG_HALFLIFE,drug.getHalfLife());
                intent.putExtra(EDIT_DRUG_HALFLIFE_UNIT,drug.getHalfLifeUnit());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddDrugActivity.class);
            startActivity(intent);
        });

    }
}
