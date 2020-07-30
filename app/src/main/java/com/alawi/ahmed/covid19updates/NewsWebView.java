package com.alawi.ahmed.covid19updates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class NewsWebView extends AppCompatActivity {

    TextView newsUrlTV;
    WebView newsWV;

    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);

        newsUrlTV = (TextView)findViewById(R.id.newsUrlTV);
        newsWV = (WebView)findViewById(R.id.newsWV);


        // to check if the url has been passed by NewsActivity
        if(getIntent().hasExtra("url")){
            url = getIntent().getExtras().getString("url");
            newsUrlTV.setText(url);
        }else{
            Toast.makeText(this,"Sorry, We could not find the URL",Toast.LENGTH_LONG).show();
            finish();
        }


        newsWV.setWebViewClient(new myWebView());
        newsWV.getSettings().setJavaScriptEnabled(true);
        newsWV.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        newsWV.loadUrl(url);


    }



    private class myWebView extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            return true;
        }

    }

}
