package com.example.medicinehalflife;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private CheckBox checkBox;
    private EditText half_life_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        half_life_input = findViewById(R.id.drug_half_life_edittext);


        TakenDrug td = new TakenDrug(13);
        td.SimulateSingleDose(1000);
        td.SimulateRepeatedDose(100, 100, 4);

        // set up spinner
        spinner = findViewById(R.id.drug_name_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.drug_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        checkBox = findViewById(R.id.custom_halflife_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Enable the user to input their own half-life if the box is checked
                half_life_input.setEnabled(isChecked);
                spinner.setEnabled(!isChecked);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String drugName = parent.getItemAtPosition(position).toString();
        int halflife = 0;
        switch (drugName) {
            case "Acetaminophen":
                // the halflife of Tylenol is 2-3 hours, which is approx 120 minutes - 180 minutes.
                // Typically stays in the blood for 3 hours.
                halflife = 180;
                break;
            case "Dextromethorphan":
                // the halflife of Dextromethorphan is typically 4 hours.
                halflife = 240;
                break;
            default:
                break;
        }
        half_life_input.setText(String.format("%s minutes", halflife));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void graph_timeline(View view) {
    }
}
