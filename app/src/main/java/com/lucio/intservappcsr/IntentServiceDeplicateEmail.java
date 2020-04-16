package com.lucio.intservappcsr;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IntentServiceDeplicateEmail extends IntentService {

    public static final String EXTRA_MESSAGE = "com.lucio.intservappcsr.extra.EXTRA_MESSAGE";
    public static final String EXTRA_RESPONSE = "com.lucio.intservappcsr.extra.EXTRA_RESPONSE";
    public static final String ACTION_RESPONSE = "com.lucio.intservappcsr.RESPONSE";
    private Handler handler;

    public IntentServiceDeplicateEmail() {
        super("IntentServiceDeplicateEmail");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String text = intent.getStringExtra(EXTRA_MESSAGE);
//        showText(text);

        Type listOfMyClassObject = new TypeToken<ArrayList<Email>>() {}.getType();

        Gson gson = new Gson();
        List<Email> outputList = gson.fromJson(text, listOfMyClassObject);

        String response = removeDuplicatedEmail(outputList);

        // processing done here….
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(EXTRA_RESPONSE, response);
        sendBroadcast(broadcastIntent);

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showText(final String message){
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private String removeDuplicatedEmail(List<Email> list){
        LinkedList<Email> tempList = new LinkedList<Email>();

        for(Email email : list){
            if(!tempList.contains(email)){
                tempList.add(email);
            }
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(tempList);

        showText(jsonString);

        return jsonString;
    }

}
