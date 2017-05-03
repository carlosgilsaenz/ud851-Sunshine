package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTextView = (TextView) findViewById(R.id.detail_text);

        // complete (2) Display the weather forecast that was passed from MainActivity
        Intent i = getIntent();
        if(i.hasExtra(Intent.EXTRA_TEXT)){
            String s = i.getStringExtra(Intent.EXTRA_TEXT);
            mTextView.setText(s);
        }
    }
}