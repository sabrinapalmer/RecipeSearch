package com.example.sabrinapalmer.miniapp2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sabrinapalmer on 3/18/18.
 */

public class ResultActivity extends AppCompatActivity {

    private ListView mListView;
    private Context mContext;
    private TextView mTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        mContext = this;

        final ArrayList<Recipe> recList = this.getIntent().getExtras().getParcelableArrayList("fullRecipeList");

        String numRecipe = "Found " + recList.size() + " recipes!";
        mTextView = findViewById(R.id.textView);
        mTextView.setText(numRecipe);

        RecipeAdapter adapter = new RecipeAdapter(this, recList);
        mListView = findViewById(R.id.recipe_list_view);
        mListView.setAdapter(adapter);









    }
    }

