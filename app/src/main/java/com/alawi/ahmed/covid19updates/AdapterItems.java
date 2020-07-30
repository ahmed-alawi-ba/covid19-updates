package com.alawi.ahmed.covid19updates;

public class AdapterItems {
    String country;
    String totalCases;
    String recovered;
    String deaths;


    //for news details
    AdapterItems(String country, String totalCases, String recovered, String deaths) {
        this.country = country;
        this.totalCases = totalCases;
        this.recovered = recovered;
        this.deaths = deaths;
    }
}
