package com.example.medicinehalflife.utils;

import androidx.annotation.NonNull;

public class HtmlContentBuilder {

    //Default height and width of a phone screen
    int webview_height = 350;
    int webview_width = 350;

    // Default dosage unit and half life unit
    String doseUnit = "mg";
    String halflifeUnit = "minutes";

    //Defaults to null so it can give the NonNull warning if we forget to set Simulation
    TakenDrug mSimulation;

    public void setDosageUnit(String dosageUnit) {
        this.doseUnit = dosageUnit;
    }

    public void setHalfLifeUnit(String halfLifeUnit) {
        this.halflifeUnit = halfLifeUnit;
    }

    public void setDims(int pixelHeight, int pixelWidth) {
        this.webview_height = pixelHeight;
        this.webview_width = pixelWidth;
    }

    public void setSimulation(@NonNull TakenDrug simulation) {
        this.mSimulation = simulation;
    }

    public String build() {

        String content = "<html> <head> <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script> <script type=\"text/javascript\">"
                + "google.charts.load('current', {'packages':['corechart']});"
                + "google.charts.setOnLoadCallback(drawChart);"
                + "function drawChart() { var data = google.visualization.arrayToDataTable("
                + "[ ['Time', 'Concentration'],"
                + GenerateDataTable(mSimulation)
                //+"[0, 100], [1, 80], [2, 65], [3, 55]"
                + "]); var options = { "
                + "title: 'Medicine Halflife Simulation'"
                + ", vAxis: {title: 'Concentration ("
                + doseUnit
                + ")'}, hAxis: {title: 'Time ("
                + halflifeUnit
                + ")'}, curveType: 'function', legend: { position: 'bottom' } }; var chart = new google.visualization.LineChart(document.getElementById('curve_chart')); chart.draw(data, options); } </script> </head> <body> <div id=\"curve_chart\" style=\"width: "
                + webview_width
                + "px; height: "
                + webview_height
                + "px\"></div> </body> </html>";
        return content;
    }

    private String GenerateDataTable(TakenDrug simulation) {
        String ret = "";
        for (int i = 0; i < simulation.concentrations.length; i++) {
            ret += "[" + i + "," + simulation.concentrations[i] + "]";
            if (i < simulation.concentrations.length - 1) {
                ret += ",";
            }
        }
        return ret;
    }

}