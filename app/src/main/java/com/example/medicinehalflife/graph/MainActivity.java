package com.example.medicinehalflife.graph;

import android.content.Context;
import android.content.Intent;
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
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.medicinehalflife.recycler.RecyclerDatabase;
import com.example.medicinehalflife.utils.HtmlContentBuilder;
import com.example.medicinehalflife.R;
import com.example.medicinehalflife.utils.TakenDrug;
import com.example.medicinehalflife.data.Drug;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // auto complete will start working from the first character
    private static final int AUTO_COMPLETE_THRESHOLD = 1;

    private Spinner dosage_spinner;
    private Spinner half_life_unit_spinner;
    private CheckBox checkBox;
    private EditText half_life_input;
    private RadioButton single_dose_button;
    private RadioGroup dosage_radio_group;

    private GraphViewModel mGraphViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final AutoCompleteTextView drug_name_AutoCompTV = findViewById(R.id.drug_name_AutoCompTV);
        // This is the placeholder adapter to fill in the temporary information from the string-array resource file.
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_item, getResources().getTextArray(R.array.drug_names));
        drug_name_AutoCompTV.setThreshold(AUTO_COMPLETE_THRESHOLD);
        drug_name_AutoCompTV.setAdapter(adapter);

        mGraphViewModel = ViewModelProviders.of(this).get(GraphViewModel.class);
        mGraphViewModel.getAllDrugs().observe(this, drugs -> {
            // Update the cached copy of the drugs in the adapter.
            List<Drug> myDrugList = mGraphViewModel.getAllDrugs().getValue();
            String[] drugNames = new String[myDrugList.size()];
            int i = 0;
            while (i < myDrugList.size()) {
                drugNames[i] = myDrugList.get(i).getName();
                i++;
            }
            // this array adapter has the string array from the Graph's ViewModel.
            final ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<>(
                    getApplicationContext(), android.R.layout.select_dialog_item, drugNames);
            drug_name_AutoCompTV.setAdapter(adapter2);

        });

        //Set the onclick for when the user actually clicks on the autocomplete choices
        if (drug_name_AutoCompTV != null) {
            drug_name_AutoCompTV.setOnItemClickListener((parent, view, position, id) -> {
                String drugName = parent.getItemAtPosition(position).toString();
                int halflife = mGraphViewModel.getHalfLife(drugName);
                half_life_input.setText(String.format("%s", halflife));
            });
        }

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
        dosage_radio_group.setOnCheckedChangeListener((radioGroup, i) -> {
            TextView everyLabel = findViewById(R.id.every_label);
            EditText frequencyEdit = findViewById(R.id.frequency_edit_text);
            TextView frequencyUnit = findViewById(R.id.frequency_unit_label);
            if (i == R.id.single_dose_radio) {
                everyLabel.setVisibility(View.GONE);
                frequencyEdit.setVisibility(View.GONE);
                frequencyUnit.setVisibility(View.GONE);
            } else {
                everyLabel.setVisibility(View.VISIBLE);
                frequencyEdit.setVisibility(View.VISIBLE);
                frequencyUnit.setVisibility(View.VISIBLE);
            }
        });

        checkBox = findViewById(R.id.custom_halflife_checkbox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Enable the user to input their own half-life if the box is checked
            half_life_input.setEnabled(isChecked);
            drug_name_AutoCompTV.setEnabled(!isChecked);
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
        if (id == R.id.action_database) {
            Intent intent = new Intent(this, RecyclerDatabase.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}


