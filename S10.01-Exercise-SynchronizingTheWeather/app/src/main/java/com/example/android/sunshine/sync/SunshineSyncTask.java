package com.example.android.sunshine.sync;

import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

//  complete (1) Create a class called SunshineSyncTask
public class SunshineSyncTask{
    //  complete (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    synchronized public static void syncWeather(Context context){
//      complete (3) Within syncWeather, fetch new weather data

        try {
            URL url = NetworkUtils.getUrl(context);
            String results = "" + NetworkUtils.getResponseFromHttpUrl(url);
            ContentValues[] values = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context,results);

            //      complete (4) If we have valid results, delete the old data and insert the new
            if(values != null || values.length != 0){
                context.getContentResolver().delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);

                context.getContentResolver().bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
