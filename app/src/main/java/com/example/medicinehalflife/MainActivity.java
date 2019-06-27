package com.example.medicinehalflife;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner drug_name_spinner;
    private Spinner dosage_spinner;
    private Spinner half_life_unit_spinner;
    private CheckBox checkBox;
    private EditText half_life_input;
    private RadioButton single_dose_button;
    private RadioGroup dosage_radio_group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        half_life_input = findViewById(R.id.drug_half_life_edittext);
        single_dose_button = findViewById(R.id.single_dose_radio);
        single_dose_button.setChecked(true);

        dosage_spinner = findViewById(R.id.dosage_unit_spinner);
        ArrayAdapter<CharSequence> unit_adapter = ArrayAdapter.createFromResource(this,R.array.dosage_units, android.R.layout.simple_spinner_item);
        unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dosage_spinner.setAdapter(unit_adapter);

        half_life_unit_spinner= findViewById(R.id.halflife_unit);
        ArrayAdapter<CharSequence> half_life_unit_adapter = ArrayAdapter.createFromResource(this,R.array.half_life_units, android.R.layout.simple_spinner_item);
        half_life_unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        half_life_unit_spinner.setAdapter(half_life_unit_adapter);
        half_life_unit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] stringArr = getResources().getStringArray(R.array.half_life_units);
                String s = stringArr[i];
                TextView frequencyUnit = findViewById(R.id.frequency_unit_label);
                frequencyUnit.setText(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dosage_radio_group = findViewById(R.id.dosage_radio_group);
        dosage_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                TextView everyLabel = findViewById(R.id.every_label);
                EditText frequencyEdit = findViewById(R.id.frequency_edit_text);
                TextView frequencyUnit = findViewById(R.id.frequency_unit_label);
                if (i==R.id.single_dose_radio){
                    everyLabel.setVisibility(View.GONE);
                    frequencyEdit.setVisibility(View.GONE);
                    frequencyUnit.setVisibility(View.GONE);
                }
                // TODO: get soft input keyboard and hide it when you click graph, using getSystemResource
                else {
                    everyLabel.setVisibility(View.VISIBLE);
                    frequencyEdit.setVisibility(View.VISIBLE);
                    frequencyUnit.setVisibility(View.VISIBLE);
                }
            }
        });


        TakenDrug td = new TakenDrug(13);
        td.SimulateSingleDose(1000);
        td.SimulateRepeatedDose(100, 100, 4);

        // set up drug_name_spinner
        drug_name_spinner = findViewById(R.id.drug_name_spinner);
        if (drug_name_spinner != null) {
            drug_name_spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.drug_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (drug_name_spinner != null) {
            drug_name_spinner.setAdapter(adapter);
        }
        checkBox = findViewById(R.id.custom_halflife_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Enable the user to input their own half-life if the box is checked
                half_life_input.setEnabled(isChecked);
                drug_name_spinner.setEnabled(!isChecked);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String drugName = parent.getItemAtPosition(position).toString();
        double halflife = 0;
        //ArrayList<String> drugNames = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.drug_names)));
        //ArrayList<String> drugHLs = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.drug_half_life_values)));
        String[] drugHLs=getResources().getStringArray(R.array.drug_half_life_values);
        halflife = Double.parseDouble(drugHLs[position]);
        half_life_input.setText(String.format("%s", halflife));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void graph_timeline(View view) {
    }
}
