package com.example.android.sunshine.sync;

import android.content.Context;
import android.content.Intent;
import android.view.View;

// complete (9) Create a class called SunshineSyncUtils
    //  complete (10) Create a public static void method called startImmediateSync
    //  complete (11) Within that method, start the SunshineSyncIntentService
public class SunshineSyncUtils{
    public static void startImmediateSync(Context context){

        Intent sunshineSyncService = new Intent(context, SunshineSyncIntentService.class);
        context.startService(sunshineSyncService);
    }
}