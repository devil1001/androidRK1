package com.example.devil1001.rk1project;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.Storage;

/**
 * Created by devil1001 on 07.03.17.
 */

public class NewsService extends IntentService {

    public static final String NEWS_LOAD_ACTION = "NEWS_LOAD_ACTION";
    public static final String NEWS_CHANGED_ACTION = "NEWS_CHANGE_ACTION";
    public static final String NEWS_ERROR = "NEWS_ERROR";
    private static NewsLoader newsLoader;

    public NewsService() {
        super("News");
        if (newsLoader == null) {
            newsLoader = new NewsLoader();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        if (intent !=null) {
            if (NEWS_LOAD_ACTION.equals(intent.getAction())) {
                try {
                    News news = newsLoader.loadNews(Storage.getInstance(this).loadCurrentTopic());
                    if (news != null) {
                        Storage.getInstance(this).saveNews(news);
                        broadcastManager.sendBroadcast(new Intent(NEWS_CHANGED_ACTION));
                    } else {
                        broadcastManager.sendBroadcast(new Intent(NEWS_ERROR));
                    }
                } catch (IOException e) {
                    broadcastManager.sendBroadcast(new Intent(NEWS_ERROR));
                    e.printStackTrace();
                }
            }
        }
    }
}
