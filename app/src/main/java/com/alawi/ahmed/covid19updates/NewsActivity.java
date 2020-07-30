package com.alawi.ahmed.covid19updates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ListView newsListView;
    ProgressBar newsProgressBar;

    ArrayList<String> newsImgUrlArray = new ArrayList<>();
    ArrayList<Bitmap> newsImgArray = new ArrayList<>();
    ArrayList<String> newsTitleArray = new ArrayList<>();
    ArrayList<String> newsSourceArray = new ArrayList<>();
    ArrayList<String> newsDateArray = new ArrayList<>();
    ArrayList<String> newsUrlArray = new ArrayList<>();


    ArrayList<AdapterItemsNews> newsListData = new ArrayList<>();
    NewsActivity.MyListAdapter myListAdapter;


    String url = "https://newsapi.org/v2/top-headlines?q=covid&country=my&apiKey=9ca9b00bad0a47b2b87a892f9d3dc8b1";

    boolean dataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsListView = (ListView)findViewById(R.id.newsListView);
        newsListView.setVisibility(View.GONE);

        newsProgressBar = (ProgressBar)findViewById(R.id.newsProgressBar);
        newsProgressBar.setVisibility(View.VISIBLE);


        if (new Network(getApplicationContext()).checkInternetConnection()){

            new Async().execute(url);
            toast("Downloading Latest News, Please Wait a few moments ...");

        }else {
            toast("Please Check Your Internet Connection and try again");
            newsProgressBar.setVisibility(View.GONE);


        }




    }



    // this method is executed to initialize the listview before getting the actual data
    private void addNewsListData(Bitmap img, String title, String source, String date){

        newsListData.add(new AdapterItemsNews(img, title, source, date));

    }






    public class Async extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConn.getInputStream());
                String str = streamToString(in);

                JSONObject obj = new JSONObject(str);

                for(int i=0; i<obj.getJSONArray("articles").length(); i++){

                    newsImgUrlArray.add(obj.getJSONArray("articles").getJSONObject(i).getString("urlToImage"));
//                    newsImgArray.add(BitmapFactory.decodeFile(newsImgUrlArray.get(i)));

                    URL imgUrl = new URL(newsImgUrlArray.get(i));

                    if(newsImgUrlArray.get(i).isEmpty() || newsImgUrlArray.get(i).equals(null)
                             || newsImgUrlArray.get(i)==null || newsImgUrlArray.get(i).equals("null")){
                        newsImgArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.menu_icon_border));
                    }else{
                        newsImgArray.add(BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream()));
                    }



                    newsTitleArray.add(obj.getJSONArray("articles").getJSONObject(i).getString("title"));
                    newsSourceArray.add(obj.getJSONArray("articles").getJSONObject(i).getJSONObject("source").getString("name"));
                    newsDateArray.add(obj.getJSONArray("articles").getJSONObject(i).getString("publishedAt"));
                    newsUrlArray.add(obj.getJSONArray("articles").getJSONObject(i).getString("url"));

                    addNewsListData(newsImgArray.get(i),newsTitleArray.get(i) + "",
                            newsSourceArray.get(i) + "",newsDateArray.get(i) + "");

                }

                dataLoaded = true;




                publishProgress();



            } catch (Exception e) {
                publishProgress(e.getMessage() + "\n" +e.toString());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            if(dataLoaded){

                // for debugging only
//                for(int i=0; i<newsImgUrlArray.size(); i++){
//                    Log.i("onProgressUpdate",newsImgUrlArray.get(i));
//                    Log.i("onProgressUpdate()", newsUrlArray.get(i) + "\n");
//                }

                myListAdapter = new NewsActivity.MyListAdapter(newsListData);
                newsListView.setAdapter(myListAdapter);

                newsProgressBar.setVisibility(View.GONE);
                newsListView.setVisibility(View.VISIBLE);


                newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent newsWebViewIntent = new Intent(getApplicationContext(),NewsWebView.class);
                        newsWebViewIntent.putExtra("url",newsUrlArray.get(i));
                        startActivity(newsWebViewIntent);
                    }
                });





            }else {
                toast("Error Retrieving Data !");
            }

            // in case if an exception was thrown
            if (values.length != 0){
                toast(values[0]);
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









    private class MyListAdapter extends BaseAdapter {
        public ArrayList<AdapterItemsNews> newsListDataAdpater ;

        public MyListAdapter(ArrayList<AdapterItemsNews>  listnewsDataAdpater) {
            this.newsListDataAdpater = listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return newsListDataAdpater.size();
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
            View myView = mInflater.inflate(R.layout.news_list_item, null);

            final AdapterItemsNews item = newsListDataAdpater.get(position);

            ImageView newsImageView = (ImageView) myView.findViewById(R.id.newsImageView);
            try {
                if(item.newsImg==null || item.newsImg.equals(null)){
                    ;
                }else{
                    newsImageView.setImageBitmap(item.newsImg);
                }
            } catch (Exception e) {
                toast(e.getMessage() + "\n" + e.toString());
            }


            TextView newsTitleTV = (TextView) myView.findViewById(R.id.newsTitleTV);
            newsTitleTV.setText(item.newsTitle);

            TextView newsSourceTV = (TextView) myView.findViewById(R.id.newsSourceTV);
            newsSourceTV.setText(item.newsSource);

            TextView newsDateTV = (TextView) myView.findViewById(R.id.newsDateTV);
            newsDateTV.setText(item.newsDate);



            return myView;
        }

    }





    ///////    Toast method     //////////
    private void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
}
