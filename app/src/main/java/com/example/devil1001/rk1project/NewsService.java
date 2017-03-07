package com.example.devil1001.rk1project;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by devil1001 on 07.03.17.
 */

public class NewsService extends IntentService {

    public static final String NEWS_LOAD_ACTION = "NEWS_LOAD_ACTION";
    public static final String NEWS_ERROR = "NEWS_ERROR";

    public NewsService() {
        super("News");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        //TODO
    }
}
