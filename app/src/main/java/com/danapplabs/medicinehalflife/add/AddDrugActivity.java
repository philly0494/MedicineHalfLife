package com.danapplabs.medicinehalflife.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.danapplabs.medicinehalflife.R;
import com.danapplabs.medicinehalflife.data.Drug;
import com.danapplabs.medicinehalflife.recycler.RecyclerDatabase;

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

        // add the drug only if the name (does not) already exist, and the name is not empty
        boolean shouldAdd = (isDuplicate == null)
                && (drug.getName().length() > 0)
                && (drug.getHalfLife().length() > 0);

        if (shouldAdd) {
            mAddDrugViewModel.addDrug(drug);
            Intent intent = new Intent(this, RecyclerDatabase.class);
            showToast(this, "New Drug Added");
            startActivity(intent);
        } else {
            showToast(this, "Invalid entry");
        }
    }

    private void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
