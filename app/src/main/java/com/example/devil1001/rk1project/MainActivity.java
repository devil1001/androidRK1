package com.example.devil1001.rk1project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;

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
                if (Storage.getInstance(MainActivity.this).loadCurrentTopic() != null) {
                    Intent intent = new Intent(MainActivity.this, NewsService.class);
                    intent.setAction(NewsService.NEWS_LOAD_ACTION);
                    startService(intent);
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean updateEnable = Storage.getInstance(MainActivity.this).loadIsUpdateInBg();
                Storage.getInstance(MainActivity.this).saveIsUpdateInBg(!updateEnable);
                if (!updateEnable) {
                    Intent myIntent = new Intent(MainActivity.this, NewsService.class);
                    myIntent.setAction(NewsService.NEWS_LOAD_ACTION);
                    PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this,  0, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.add(Calendar.SECOND, 60);
                    long frequency= 60 * 1000;
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);
                    Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_SHORT).show();
                } else {
                    Intent myIntent = new Intent(MainActivity.this, NewsService.class);
                    myIntent.setAction(NewsService.NEWS_LOAD_ACTION);
                    PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this,  0, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this, "Stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (intent.getAction().equals(NewsService.NEWS_CHANGED_ACTION)) {
                News news = Storage.getInstance(MainActivity.this).getLastSavedNews();
                headerNews.setText(news.getTitle());
                dateNews.setText(DateFormat.format("MM/dd/yyyy HH:mm:ss", new Date(news.getDate())).toString());
                bodyNews.setText(news.getBody());
            }
            if (intent.getAction().equals(NewsService.NEWS_ERROR)) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(NewsService.NEWS_CHANGED_ACTION);
        filter.addAction(NewsService.NEWS_ERROR);
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(receiver, filter);
        if (Storage.getInstance(MainActivity.this).loadCurrentTopic().equals("")) {
            Storage.getInstance(MainActivity.this).saveCurrentTopic(Topics.IT);
        }
        News news = Storage.getInstance(MainActivity.this).getLastSavedNews();
        if (news != null) {
            headerNews.setText(news.getTitle());
            dateNews.setText(DateFormat.format("MM/dd/yyyy HH:mm:ss", new Date(news.getDate())).toString());
            bodyNews.setText(news.getBody());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, NewsService.class);
        intent.setAction(NewsService.NEWS_LOAD_ACTION);
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

}
