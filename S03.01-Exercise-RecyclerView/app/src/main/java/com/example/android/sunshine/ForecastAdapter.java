package com.example.android.sunshine;

import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by Mick Jagger on 4/27/2017.
 */
// Within ForecastAdapter.java /////////////////////////////////////////////////////////////////
// complete (15) Add a class file called ForecastAdapter
// complete (22) Extend RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>

// complete (23) Create a private string array called mWeatherData

// complete (47) Create the default constructor (we will pass in parameters in a later lesson)

// complete (16) Create a class within ForecastAdapter called ForecastAdapterViewHolder
// complete (17) Extend RecyclerView.ViewHolder
// complete (24) Override onCreateViewHolder
// complete (25) Within onCreateViewHolder, inflate the list item xml into a view
// complete (26) Within onCreateViewHolder, return a new ForecastAdapterViewHolder with the above view passed in as a parameter

// complete (27) Override onBindViewHolder
// complete (28) Set the text of the TextView to the weather for this list item's position

// complete (29) Override getItemCount
// complete (30) Return 0 if mWeatherData is null, or the size of mWeatherData if it is not null

// complete (31) Create a setWeatherData method that saves the weatherData to mWeatherData
// complete (32) After you save mWeatherData, call notifyDataSetChanged
// Within ForecastAdapter.java /////////////////////////////////////////////////////////////////

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private String[] mWeatherData;

    public ForecastAdapter(){}

    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_list_item, parent, false);

        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {
        String string = mWeatherData[position];
        holder.mWeatherTextView.setText(string);
    }

    @Override
    public int getItemCount() {
        if(mWeatherData == null){
            return 0;
        }
        return mWeatherData.length;
    }

    void setWeatherData(String[] weatherData){
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }
    // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////
    // complete (18) Create a public final TextView variable called mWeatherTextView
    // complete (19) Create a constructor for this class that accepts a View as a parameter
    // complete (20) Call super(view) within the constructor for ForecastAdapterViewHolder
    // complete (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
    // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mWeatherTextView;

        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            mWeatherTextView = (TextView) itemView.findViewById(R.id.tv_weather_data);
        }
    }
}
