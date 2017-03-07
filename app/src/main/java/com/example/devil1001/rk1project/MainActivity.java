package com.example.devil1001.rk1project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button categoryButton;
    Button refreshButton;
    Button startButton;
    TextView headerNews;
    TextView bodyNews;
    TextView dateNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryButton = (Button) findViewById(R.id.chooseButton);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        startButton = (Button) findViewById(R.id.startButton);
        headerNews = (TextView) findViewById(R.id.headerNews);
        bodyNews = (TextView) findViewById(R.id.bodyNews);
        dateNews = (TextView) findViewById(R.id.dateNews);

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_SHORT).show();
            }
        });

        // TODO: if on category choosen
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {

            }
        };

    @Override
    protected void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

}
