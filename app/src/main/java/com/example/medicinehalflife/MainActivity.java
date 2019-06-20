package com.example.medicinehalflife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TakenDrug td = new TakenDrug(13);
        td.SimulateSingleDose(1000);
        td.SimulateRepeatedDose(100, 100,4);
    }

}
