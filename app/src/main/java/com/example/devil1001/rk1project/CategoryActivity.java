package com.example.devil1001.rk1project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;

/**
 * Created by devil1001 on 07.03.17.
 */

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{

    Button firstCategory;
    Button secondCategory;
    Button thirdCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        firstCategory = (Button) findViewById(R.id.firstCategory);
        secondCategory = (Button) findViewById(R.id.secondCategory);
        thirdCategory = (Button) findViewById(R.id.thirdCategory);

        firstCategory.setOnClickListener(this);
        secondCategory.setOnClickListener(this);
        thirdCategory.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firstCategory:
                Storage.getInstance(CategoryActivity.this).saveCurrentTopic(Topics.AUTO);
                break;
            case R.id.secondCategory:
                Storage.getInstance(CategoryActivity.this).saveCurrentTopic(Topics.IT);
                break;
            case R.id.thirdCategory:
                Storage.getInstance(CategoryActivity.this).saveCurrentTopic(Topics.HEALTH);
                break;
        }
        finish();
    }
}
