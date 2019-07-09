package com.example.medicinehalflife.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicinehalflife.R;
import com.example.medicinehalflife.data.Drug;
import com.example.medicinehalflife.recycler.RecyclerDatabase;

public class AddDrugActivity extends AppCompatActivity {

    private AddDrugViewModel mAddDrugViewModel;
    private Spinner add_drug_unit_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);
        mAddDrugViewModel = ViewModelProviders.of(this).get(AddDrugViewModel.class);
        add_drug_unit_spinner = findViewById(R.id.add_drug_half_life_unit_spinner);
        ArrayAdapter<CharSequence> half_life_unit_adapter = ArrayAdapter.createFromResource(
                this, R.array.half_life_units, android.R.layout.simple_spinner_item);
        half_life_unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_drug_unit_spinner.setAdapter(half_life_unit_adapter);
    }

    public void saveNewDrug(View view) {
        final EditText name = findViewById(R.id.add_drug_name_edit_text);
        final EditText halfLife = findViewById(R.id.add_drug_half_life_edit_text);
        String unit = add_drug_unit_spinner.getSelectedItem().toString();

        final Drug drug = new Drug(name.getText().toString(),
                halfLife.getText().toString(),
                unit);
        Drug isDuplicate = mAddDrugViewModel.getDrugByName(name.getText().toString());

        // Don't add the drug if the name already exist
        if (isDuplicate == null) {
            mAddDrugViewModel.addDrug(drug);
            Intent intent = new Intent(this, RecyclerDatabase.class);
            Toast.makeText(this, "New Drug Added", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {
            Toast.makeText(this, "New Drug Name must be Unique", Toast.LENGTH_SHORT).show();
        }
    }
}
