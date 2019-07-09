package com.example.medicinehalflife.add;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.example.medicinehalflife.R;
import com.example.medicinehalflife.data.Drug;

public class AddDrugActivity extends AppCompatActivity {

    private AddDrugViewModel mAddDrugViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);
        mAddDrugViewModel = ViewModelProviders.of(this).get(AddDrugViewModel.class);
    }

    public void saveNewDrug(View view) {
        final EditText name = findViewById(R.id.add_drug_name_edit_text);
        final EditText halfLife = findViewById(R.id.add_drug_half_life_edit_text);
        final EditText unit = findViewById(R.id.add_drug_half_life_unit_edit_text);
        final Drug drug = new Drug(name.getText().toString(),
                halfLife.getText().toString(),
                unit.getText().toString());
        Drug isDuplicate = mAddDrugViewModel.getDrugByName(name.getText().toString());

        // Don't add the drug if the name already exist
        if (isDuplicate != null){
            mAddDrugViewModel.addDrug(drug);
        } else {
            Toast.makeText(this,"New Drug Name must be Unique",Toast.LENGTH_SHORT).show();
        }
    }
}
