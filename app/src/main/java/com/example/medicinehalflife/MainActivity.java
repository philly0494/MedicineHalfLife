package com.example.medicinehalflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity{

    private AutoCompleteTextView drug_name_AutoCompTV;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        half_life_input = findViewById(R.id.drug_half_life_edittext);
        single_dose_button = findViewById(R.id.single_dose_radio);
        single_dose_button.setChecked(true);

        dosage_spinner = findViewById(R.id.dosage_unit_spinner);
        ArrayAdapter<CharSequence> unit_adapter = ArrayAdapter.createFromResource(
                this, R.array.dosage_units, android.R.layout.simple_spinner_item);
        unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dosage_spinner.setAdapter(unit_adapter);

        half_life_unit_spinner = findViewById(R.id.halflife_unit);
        ArrayAdapter<CharSequence> half_life_unit_adapter = ArrayAdapter.createFromResource(
                this, R.array.half_life_units, android.R.layout.simple_spinner_item);
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
                if (i == R.id.single_dose_radio) {
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


        // set up drug_name_AutoCompleteTV
        drug_name_AutoCompTV = findViewById(R.id.drug_name_AutoCompTV);
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.select_dialog_item, getResources().getTextArray(R.array.drug_names));
        drug_name_AutoCompTV.setThreshold(1); // will start working from the first character
        drug_name_AutoCompTV.setAdapter(adapter);

        //Set the onclick for when the user actually clicks on the autocomplete choices
        if (drug_name_AutoCompTV != null) {
            drug_name_AutoCompTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String drugName = parent.getItemAtPosition(position).toString();
                    int halflife = 0;
                    // Gets the array of drug names as an ArrayList, we need it to use the indexOf method.
                    ArrayList<String> drugNames = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.drug_names)));
                    // Get the real position of the drug name in our drug names array
                    int realPos = drugNames.indexOf(drugName);

                    //Find the correlating half life value and set it as the half life of the edit text. Warning: drug_names array and drug_half_life_values size must match
                    String[] drugHLs = getResources().getStringArray(R.array.drug_half_life_values);
                    // Warning: Do not use Position. It is the position of the "GENERATED LIST", meaning if the auto complete list only has one item, that POS is always 0.
                    halflife = Integer.parseInt(drugHLs[realPos]);
                    half_life_input.setText(String.format("%s", halflife));
                }
            });
        }
        checkBox = findViewById(R.id.custom_halflife_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Enable the user to input their own half-life if the box is checked
                half_life_input.setEnabled(isChecked);
                drug_name_AutoCompTV.setEnabled(!isChecked);
            }
        });

    }

    public void graph_timeline() {

        try {
            int hl;
            int initialDose;
            int freq;

            final EditText hfEditText = findViewById(R.id.drug_half_life_edittext);

            hl = Integer.parseInt(hfEditText.getText().toString());
            final EditText doseEditText = findViewById(R.id.dosage_edittext);
            initialDose = Integer.parseInt(doseEditText.getText().toString());

            final RadioButton singleDoseRadio = findViewById(R.id.single_dose_radio);
            TakenDrug simulation = new TakenDrug(hl);
            if (singleDoseRadio.isChecked()) {
                simulation.SimulateSingleDose(initialDose);
            } else {
                final EditText freqEditText = findViewById(R.id.frequency_edit_text);
                freq = Integer.parseInt(freqEditText.getText().toString());
                simulation.SimulateRepeatedDose(initialDose, initialDose, freq);
            }

            WebView webview = findViewById(R.id.webview_graph);

            int webview_height = Integer.parseInt(getResources().getString(R.string.webview_height));
            int webview_width = Integer.parseInt(getResources().getString(R.string.webview_width));

            String doseUnit = dosage_spinner.getSelectedItem().toString();
            String halflifeUnit = half_life_unit_spinner.getSelectedItem().toString();

            // Moved the content to it's own class
            HtmlContentBuilder builder = new HtmlContentBuilder();
            builder.setDims(webview_height, webview_width);
            builder.setDosageUnit(doseUnit);
            builder.setHalfLifeUnit(halflifeUnit);
            builder.setSimulation(simulation);
            String builtContent = builder.build();

            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webview.requestFocusFromTouch();
            webview.loadDataWithBaseURL("file:///android_asset/", builtContent, "text/html", "utf-8", null);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Snackbar.make(findViewById(R.id.coordinatorLayout_main), "Please give all information", Snackbar.LENGTH_LONG)
                    .show();
        }

        // Gets input manager from android's system services
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        // Hides the keyboard, but do not override "force keyboard" flags
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(findViewById(R.id.action_graph).getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_graph) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connMgr != null) {
                networkInfo = connMgr.getActiveNetworkInfo();
            }

            // Only try to graph using the API if we have internet
            if (networkInfo != null && networkInfo.isConnected()) {
                graph_timeline();
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Found", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        if (id == R.id.action_database){
            Intent intent = new Intent(this, DrugDatabase.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}


