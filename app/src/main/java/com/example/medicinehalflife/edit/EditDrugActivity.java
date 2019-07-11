package com.example.medicinehalflife.edit;

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
import com.example.medicinehalflife.R;
import com.example.medicinehalflife.data.Drug;
import com.example.medicinehalflife.recycler.RecyclerDatabase;

public class EditDrugActivity extends AppCompatActivity {

    private EditText editDrugName;
    private EditText editDrugHalfLife;
    private Spinner edit_drug_unit_spinner;
    private EditDrugViewModel mEditDrugViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drug);
        mEditDrugViewModel = ViewModelProviders.of(this).get(EditDrugViewModel.class);
        Intent intent = getIntent();
        String drugName = intent.getStringExtra(RecyclerDatabase.EDIT_DRUG_NAME);
        String drugHalfLife = intent.getStringExtra(RecyclerDatabase.EDIT_DRUG_HALFLIFE);
        String drugHalfLifeUnit = intent.getStringExtra(RecyclerDatabase.EDIT_DRUG_HALFLIFE_UNIT);
        editDrugName = findViewById(R.id.edit_drug_name_edit_text);
        editDrugName.setText(drugName);
        editDrugHalfLife = findViewById(R.id.edit_drug_half_life_edit_text);
        editDrugHalfLife.setText(drugHalfLife);

        edit_drug_unit_spinner = findViewById(R.id.edit_drug_half_life_unit_spinner);
        ArrayAdapter<CharSequence> half_life_unit_adapter = ArrayAdapter.createFromResource(
                this, R.array.half_life_units, android.R.layout.simple_spinner_item);
        half_life_unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_drug_unit_spinner.setAdapter(half_life_unit_adapter);
        setSpinText(edit_drug_unit_spinner,drugHalfLifeUnit);
    }

    public void saveEditDrug(View view) {
        final EditText name = findViewById(R.id.edit_drug_name_edit_text);
        final EditText halfLife = findViewById(R.id.edit_drug_half_life_edit_text);
        String unit = edit_drug_unit_spinner.getSelectedItem().toString();

        final Drug drug = new Drug(name.getText().toString(),
                halfLife.getText().toString(),
                unit);

        mEditDrugViewModel.editDrug(drug);

        Intent intent = new Intent(this, RecyclerDatabase.class);
        showToast(this, "Drug Edited");
        startActivity(intent);

    }

    private void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    void setSpinText(Spinner spin, String text) {
        for (int i = 0; i < spin.getAdapter().getCount(); i++) {
            if (spin.getAdapter().getItem(i).toString().contains(text)) {
                spin.setSelection(i);
            }
        }
    }

}
