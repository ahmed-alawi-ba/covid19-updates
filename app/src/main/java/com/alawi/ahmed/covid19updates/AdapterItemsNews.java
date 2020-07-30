package com.alawi.ahmed.covid19updates;

import android.graphics.Bitmap;

public class AdapterItemsNews {

    Bitmap newsImg;
    String newsTitle;
    String newsSource;
    String newsDate;


    public AdapterItemsNews(Bitmap newsImg, String newsTitle, String newsSource, String newsDate){
        this.newsImg = newsImg;
        this.newsTitle = newsTitle;
        this.newsSource = newsSource;
        this.newsDate = newsDate;
    }
}
