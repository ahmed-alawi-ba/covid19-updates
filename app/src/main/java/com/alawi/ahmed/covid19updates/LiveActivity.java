package com.alawi.ahmed.covid19updates;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;



import android.os.AsyncTask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class LiveActivity extends AppCompatActivity {

    // layout views declaration
    ListView countriesListView;

    TextView lastUpdateTV;
    TextView refreshTV;

    ProgressBar liveProgressBar;



    // ListView declarations
    ArrayList<AdapterItems> countriesListData = new ArrayList<>();
    LiveActivity.MyListAdapter myListAdapter;





    ArrayList<String> countries = new ArrayList<>();
    ArrayList<Integer> cases = new ArrayList<>();
    ArrayList<Integer> recovered = new ArrayList<>();
    ArrayList<Integer> deaths = new ArrayList<>();





    String url = "https://api.covid19api.com/summary";


    boolean dataInserted = false;

    boolean dataLoaded = false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);


        lastUpdateTV = (TextView)findViewById(R.id.lastUpdateTV);
        refreshTV = (TextView)findViewById(R.id.refreshTV);
        countriesListView = (ListView)findViewById(R.id.countriesListView);
        countriesListView.setVisibility(View.GONE);

        liveProgressBar = (ProgressBar)findViewById(R.id.liveProgressBar);
        liveProgressBar.setVisibility(View.VISIBLE);


        refreshTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new Network(getApplicationContext()).checkInternetConnection()){
                    if(dataLoaded == false){
                        new Async().execute(url);
                        liveProgressBar.setVisibility(View.VISIBLE);
                    }


                }else {
                    toast("Please Check Your Internet Connection and try again");
                    liveProgressBar.setVisibility(View.GONE);

                }
            }
        });

//        addPrayersListData();


        // to check if user has an internet connection
        if (new Network(getApplicationContext()).checkInternetConnection()){

            new Async().execute(url);
            toast("Downloading Latest Data, Please Wait a few moments ...");

        }else {
            toast("Please Check Your Internet Connection and try again");
            liveProgressBar.setVisibility(View.GONE);


        }


    }






    // this method is executed to initialize the listview before getting the actual data
    private void addCountryListData(String name, String cases, String recovered, String deaths){

        countriesListData.add(new AdapterItems(name, cases, recovered, deaths));

    }


    private String setLastUpdate(){

        String date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());


        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        date = formatter.format(calendar.getTime());

        return date;
    }







    ///////  Async Task class to get the data from the server in the background    //////////////
    public class Async extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConn.getInputStream());
                String str = streamToString(in);

                JSONObject obj = new JSONObject(str);

                // to add Global values
                addCountryListData("Worldwide",obj.getJSONObject("Global").getInt("TotalConfirmed") + "",
                        obj.getJSONObject("Global").getInt("TotalRecovered") + "",
                        obj.getJSONObject("Global").getInt("TotalDeaths") + "");

                // to loop through the countries values
                for(int i=0; i<obj.getJSONArray("Countries").length(); i++){

                    countries.add(obj.getJSONArray("Countries").getJSONObject(i).getString("Country"));
                    cases.add(obj.getJSONArray("Countries").getJSONObject(i).getInt("TotalConfirmed"));
                    recovered.add(obj.getJSONArray("Countries").getJSONObject(i).getInt("TotalRecovered"));
                    deaths.add(obj.getJSONArray("Countries").getJSONObject(i).getInt("TotalDeaths"));

                    addCountryListData(countries.get(i),cases.get(i) + "",
                            recovered.get(i) + "",deaths.get(i) + "");

                }

                dataInserted = true;



                publishProgress();



            } catch (Exception e) {
                publishProgress(e.getMessage() + "\n" +e.toString());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            if(dataInserted){
                myListAdapter = new LiveActivity.MyListAdapter(countriesListData);
                countriesListView.setAdapter(myListAdapter);

                liveProgressBar.setVisibility(View.GONE);
                countriesListView.setVisibility(View.VISIBLE);
                dataLoaded = true;

                lastUpdateTV.setText(setLastUpdate());

            }else {
                toast("Error Retrieving Data !");
            }

            // in case if an exception was thrown
            if (values.length != 0){
                toast(values[0]);
                finish();
            }


        }
    }

    public String streamToString(InputStream input) {
        BufferedReader buf = new BufferedReader(new InputStreamReader(input));
        String line;
        String text = "";

        try {
            while ((line = buf.readLine()) != null) {
                text += line + "\n";
            }
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }








    ///////    Toast method     ////////////////
    private void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }







    private class MyListAdapter extends BaseAdapter{
        public ArrayList<AdapterItems> countriesListDataAdpater ;

        public MyListAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.countriesListDataAdpater = listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return countriesListDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.countries_list_item, null);

            final AdapterItems item = countriesListDataAdpater.get(position);

            TextView country = (TextView) myView.findViewById(R.id.countryTV);
            country.setText(item.country);

            TextView totalCases = (TextView) myView.findViewById(R.id.totalCasesTV);
            totalCases.setText(item.totalCases);

            TextView recovered = (TextView) myView.findViewById(R.id.recoveredTV);
            recovered.setText(item.recovered);

            TextView deaths = (TextView) myView.findViewById(R.id.deathsTV);
            deaths.setText(item.deaths);


            return myView;
        }

    }




}
