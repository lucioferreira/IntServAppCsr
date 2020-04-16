package com.lucio.intservappcsr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Receiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter(IntentServiceDeplicateEmail.ACTION_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new Receiver();
        registerReceiver(receiver, filter);
    }

    public void onClickTest(View view) {
        String message = "Json comes here!!!";

        Gson gson = new Gson();
        String jsonString = gson.toJson(populateEmailList());

        Intent intent = new Intent(this, IntentServiceDeplicateEmail.class);
        intent.putExtra(IntentServiceDeplicateEmail.EXTRA_MESSAGE, jsonString);
        startService(intent);
    }

    private ArrayList<Email> populateEmailList() {
        ArrayList<Email> list = new ArrayList<Email>();

        list.add(new Email("title 1", "message 1"));
        list.add(new Email("title 2", "message 2"));
        list.add(new Email("title 2", "message 2"));
        list.add(new Email("title 4", "message 4"));
        list.add(new Email("title 5", "message 5"));
        list.add(new Email("title 6", "message 6"));
        list.add(new Email("title 5", "message 5"));
        list.add(new Email("title 8", "message 8"));

        return list;
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(IntentServiceDeplicateEmail.EXTRA_RESPONSE);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
