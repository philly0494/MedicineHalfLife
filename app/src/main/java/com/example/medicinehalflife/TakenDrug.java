package com.example.medicinehalflife;
//dimensionless computation of concentration
public class TakenDrug {
    //Drug drug;
    String Name;
    double[] concentrations;
    int halflife;
    double maxlevel;

    public TakenDrug(int hl){
        this.halflife = hl;
    }

    //Simulates concentration over time.  Requires halflife
    public void SimulateSingleDose(int initialDose){
        concentrations = new double[60*24];  //minute-by-minute for 24 hours
        concentrations[0]= initialDose;
        maxlevel = initialDose;
        for (int i = 1; i < concentrations.length; i++) {
            double decayRate = concentrations[i - 1] * Math.log(2) / halflife;
            concentrations[i] = concentrations[i - 1] - decayRate;
        }
    }

    public void SimulateRepeatedDose(int initialDose, int repeatedDose, int frequency) {
        concentrations = new double[60 * 24];  //minute-by-minute for 24 hours
        concentrations[0] = initialDose;
        for (int i = 1; i < concentrations.length; i++) {
            double decayRate = concentrations[i - 1] * Math.log(2) / halflife;
            concentrations[i] = concentrations[i - 1] - decayRate;
            if (i % frequency == 0) {
                concentrations[i] += repeatedDose;
            }
            if (concentrations[i] > maxlevel) {
                maxlevel = concentrations[i];
            }
        }
    }
}
