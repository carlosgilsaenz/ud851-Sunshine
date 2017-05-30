/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.SunshineDateUtils;
import com.example.android.sunshine.utilities.SunshineWeatherUtils;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
//      TODO (21) Implement LoaderManager.LoaderCallbacks<Cursor>

    /*
     * In this Activity, you can share the selected day's forecast. No social sharing is complete
     * without using a hashtag. #BeTogetherNotTheSame
     */
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

//  TODO (18) Create a String array containing the names of the desired data columns from our ContentProvider
    private String[] projection = new String[]{
        WeatherContract.WeatherEntry.COLUMN_DATE,
        WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
        WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
        WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
        WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
        WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
        WeatherContract.WeatherEntry.COLUMN_DEGREES,
        WeatherContract.WeatherEntry.COLUMN_PRESSURE};
//  TODO (19) Create constant int values representing each column name's position above
    static final int DATE_POSITION = 0;
    static final int DESCRIPTION_POSITION = 1;
    static final int TEMP_HIGH_POSITION = 2;
    static final int TEMP_LOW_POSITION = 3;
    static final int HUMIDITY_POSITION = 4;
    static final int WIND_POSITION = 5;
    static final int DEGREES_POSITION = 6;
    static final int PRESSURE_POSITION = 7;
//  TODO (20) Create a constant int to identify our loader used in DetailActivity
    static final int DETAIL_WEATHER_LOADER = 100;
    /* A summary of the forecast that can be shared by clicking the share button in the ActionBar */
    private String mForecastSummary;

//  TODO (15) Declare a private Uri field called mUri
    private Uri mUri;
//  TODO (10) Remove the mWeatherDisplay TextView declaration

//  TODO (11) Declare TextViews for the date, description, high, low, humidity, wind, and pressure
    TextView mDate_Textview;
    TextView mDescription_TextView;
    TextView mTempHigh_TextView;
    TextView mTempLow_TextView;
    TextView mHumidity_TextView;
    TextView mWind_TextView;
    TextView mPressure_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//      TODO (12) Remove mWeatherDisplay TextView
//      TODO (13) Find each of the TextViews by ID
        mDate_Textview = (TextView) findViewById(R.id.display_date);
        mDescription_TextView = (TextView) findViewById(R.id.display_description);
        mTempHigh_TextView = (TextView) findViewById(R.id.display_temp_high);
        mTempLow_TextView = (TextView) findViewById(R.id.display_temp_low);
        mHumidity_TextView = (TextView) findViewById(R.id.display_humidity);
        mWind_TextView = (TextView) findViewById(R.id.display_wind);
        mPressure_TextView = (TextView) findViewById(R.id.display_pressure);
//      TODO (14) Remove the code that checks for extra text

//      TODO (16) Use getData to get a reference to the URI passed with this Activity's Intent
        mUri = getIntent().getData();
//      TODO (17) Throw a NullPointerException if that URI is null
        if(mUri == null){throw new NullPointerException("No Uri supplied");}
//      TODO (35) Initialize the loader for DetailActivity
        getSupportLoaderManager().initLoader(DETAIL_WEATHER_LOADER,null,this);
    }

    /**
     * This is where we inflate and set up the menu for this Activity.
     *
     * @param menu The options menu in which you place your items.
     *
     * @return You must return true for the menu to be displayed;
     *         if you return false it will not be shown.
     *
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    /**
     * Callback invoked when a menu item was selected from this Activity's menu. Android will
     * automatically handle clicks on the "up" button for us so long as we have specified
     * DetailActivity's parent Activity in the AndroidManifest.
     *
     * @param item The menu item that was selected by the user
     *
     * @return true if you handle the menu click here, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Get the ID of the clicked item */
        int id = item.getItemId();

        /* Settings menu item clicked */
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        /* Share menu item clicked */
        if (id == R.id.action_share) {
            Intent shareIntent = createShareForecastIntent();
            startActivity(shareIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Uses the ShareCompat Intent builder to create our Forecast intent for sharing.  All we need
     * to do is set the type, text and the NEW_DOCUMENT flag so it treats our share as a new task.
     * See: http://developer.android.com/guide/components/tasks-and-back-stack.html for more info.
     *
     * @return the Intent to use to share our weather forecast
     */
    private Intent createShareForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mForecastSummary + FORECAST_SHARE_HASHTAG)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;
    }

//  TODO (22) Override onCreateLoader
//          TODO (23) If the loader requested is our detail loader, return the appropriate CursorLoader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case DETAIL_WEATHER_LOADER:
                return new CursorLoader(this,
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader " + id + " not implemented");
        }
    }

//  TODO (24) Override onLoadFinished
//      TODO (25) Check before doing anything that the Cursor has valid data
//      TODO (26) Display a readable data string
//      TODO (27) Display the weather description (using SunshineWeatherUtils)
//      TODO (28) Display the high temperature
//      TODO (29) Display the low temperature
//      TODO (30) Display the humidity
//      TODO (31) Display the wind speed and direction
//      TODO (32) Display the pressure
//      TODO (33) Store a forecast summary in mForecastSummary

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null && data.moveToFirst()){
            long dateID = data.getLong(DATE_POSITION);
            String date = SunshineDateUtils.getFriendlyDateString(this,dateID,true);
            mDate_Textview.setText(date);

            int descriptionId = data.getInt(DESCRIPTION_POSITION);
            String description = SunshineWeatherUtils.getStringForWeatherCondition(this,descriptionId);
            mDescription_TextView.setText(description);

            double tempHighID = data.getDouble(TEMP_HIGH_POSITION);
            String tempHigh = SunshineWeatherUtils.formatTemperature(this, tempHighID);
            mTempHigh_TextView.setText(tempHigh);

            double tempLowID = data.getDouble(TEMP_LOW_POSITION);
            String tempLow = SunshineWeatherUtils.formatTemperature(this, tempLowID);
            mTempLow_TextView.setText(tempLow);

            float humidityID = data.getFloat(HUMIDITY_POSITION);
            mHumidity_TextView.setText(String.valueOf(humidityID));

            float windID = data.getFloat(WIND_POSITION);
            float degreesID = data.getFloat(DEGREES_POSITION);
            String wind = SunshineWeatherUtils.getFormattedWind(this,windID,degreesID);
            mWind_TextView.setText(wind);

            float pressureID = data.getFloat(PRESSURE_POSITION);
            mPressure_TextView.setText(String.valueOf(pressureID));

            mForecastSummary = String.format("%s - %s - %s/%s",
                    date, description, tempHigh, tempLow);
        }
    }


//  TODO (34) Override onLoaderReset, but don't do anything in it yet

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}