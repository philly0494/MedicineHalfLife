package com.example.medicinehalflife.utils;
//dimensionless computation of concentration
public class TakenDrug {
    //Drug drug;
    String Name;
    double[] concentrations;
    int hlValue;
    double maxlevel;
    String hlUnit;

    public TakenDrug(int hl, String unit){
        this.hlValue = hl;
        this.hlUnit = unit;
    }

    //Simulates concentration over time.  Requires halflife
    public void SimulateSingleDose(int initialDose){
        concentrations = new double[calculateDomain()];
        concentrations[0]= initialDose;
        maxlevel = initialDose;
        for (int i = 1; i < concentrations.length; i++) {
            double decayRate = concentrations[i - 1] * Math.log(2) / hlValue;
            concentrations[i] = concentrations[i - 1] - decayRate;
        }
    }

    public void SimulateRepeatedDose(int initialDose, int repeatedDose, int frequency) {
        concentrations = new double[calculateDomain()];
        concentrations[0] = initialDose;
        for (int i = 1; i < concentrations.length; i++) {
            double decayRate = concentrations[i - 1] * Math.log(2) / hlValue;
            concentrations[i] = concentrations[i - 1] - decayRate;
            if (i % frequency == 0) {
                concentrations[i] += repeatedDose;
            }
            if (concentrations[i] > maxlevel) {
                maxlevel = concentrations[i];
            }
        }
    }
    private int calculateDomain()
    {
        //these buckets are arbitrary, but very easy to adjust
        switch(this.hlUnit)
        {
            case "mins":
                return 60*24;
            case "hrs":
                if (this.hlValue < 10)
                    return 24;
                else
                    return 48;
            case "days":
                if (this.hlValue < 12)
                    return 30;
                else
                    return 60;
        }
        return 60*24;
    }
}
