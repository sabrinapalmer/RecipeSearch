package com.example.sabrinapalmer.miniapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabrinapalmer on 3/18/18.
 */

public class SearchActivity extends AppCompatActivity {

    private Spinner spin1;
    private Spinner spin2;
    private Spinner spin3;
    Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipe.json", this);

        mContext = this;

        spin1 = findViewById(R.id.spinner4);
        spin2 = findViewById(R.id.spinner5);
        spin3 = findViewById(R.id.spinner6);

        List<String> spinlist1 = new ArrayList<>();
        spinlist1.add(" ");
        List<String> spinlist2 = new ArrayList<>();
        spinlist2.add(" ");
        List<String> spinlist3 = new ArrayList<>();
        spinlist3.add(" ");

        for(int i = 0; i < recipeList.size(); i++){
            if (spinlist1.contains(recipeList.get(i).label)){

            }
            else{
                spinlist1.add(recipeList.get(i).label);
            }
        }

        spinlist2.add("less than 4");
        spinlist2.add("4-6");
        spinlist2.add("7-9");
        spinlist2.add("more than 10");

        spinlist3.add("30 minutes or less");
        spinlist3.add("less than 1 hour");
        spinlist3.add("more than 1 hour");

        ArrayAdapter<String> spinadapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinlist1);
        ArrayAdapter<String> spinadapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinlist2);
        ArrayAdapter<String> spinadapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinlist3);

        spinadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin1.setAdapter(spinadapter1);
        spin2.setAdapter(spinadapter2);
        spin3.setAdapter(spinadapter3);



        Button searchButton = (Button) findViewById(R.id.button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Recipe> newList = new ArrayList<Recipe>();
                for(int i = 0; i < recipeList.size(); i++) {
                    if (spin1.getSelectedItem().equals(" ") || spin1.getSelectedItem().equals(recipeList.get(i).label)) {
                        newList.add(recipeList.get(i));
                    }
                }
                ArrayList<Recipe> newList2 = new ArrayList<Recipe>();
                for(int l = 0; l < newList.size(); l ++){
                    if(spin2.getSelectedItem().equals(" ")){
                        newList2.add(newList.get(l));
                    }
                    if(spin2.getSelectedItem().equals("less than 4") && newList.get(l).servings < 4){
                        newList2.add(newList.get(l));
                    }
                    if(spin2.getSelectedItem().equals("4-6") && newList.get(l).servings >= 4 && newList.get(l).servings <= 6){
                        newList2.add(newList.get(l));
                    }
                    if(spin2.getSelectedItem().equals("7-9") && newList.get(l).servings >= 7 && newList.get(l).servings <= 9){
                        newList2.add(newList.get(l));
                    }
                    if(spin2.getSelectedItem().equals("more than 10") && newList.get(l).servings >= 10){
                        newList2.add(newList.get(l));
                    }
                }
                ArrayList<Recipe> newList3 = new ArrayList<Recipe>();
                for(int n = 0; n < newList2.size(); n++){
                    String weirdTime = newList2.get(n).prepTime;
                    String[] arr = weirdTime.split(" ");
                    if(spin3.getSelectedItem().equals(" ")){
                        newList3.add(newList2.get(n));
                    }
                    if(spin3.getSelectedItem().equals("30 minutes or less") && arr[1].startsWith("m") && Integer.parseInt(arr[0]) <= 30){
                        newList3.add(newList2.get(n));
                    }
                    if(spin3.getSelectedItem().equals("less than 1 hour") && arr[1].startsWith("m")){
                        newList3.add(newList2.get(n));
                    }
                    if(spin3.getSelectedItem().equals("more than 1 hour") && arr[1].startsWith("h")){
                        newList3.add(newList2.get(n));
                    }

                }



                Intent searchIntent = new Intent(mContext, ResultActivity.class);
                searchIntent.putParcelableArrayListExtra("fullRecipeList", (ArrayList<? extends Parcelable>) newList3);
                startActivity(searchIntent);
            }
        });


    }

}
