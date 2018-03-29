package com.example.sabrinapalmer.miniapp2; /**
 * Created by sabrinapalmer on 3/18/18.
 */
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class Recipe implements Parcelable {


    // instance variables or fields
    public String title;
    public String imageUrl;
    public String instructionUrl;
    public String description;
    public String label;
    public int servings;
    public String prepTime;

    public Recipe() {

    }

    // constructor
    // default

    // method
    // static methods that read the json file in and load into com.example.sabrinapalmer.miniapp2.Recipe

    // static method that loads our recipes.json using the helper method
    // this method will return an array list of recipes constructed from the JSON
    // file
    public static ArrayList<Recipe> getRecipesFromFile(String filename, Context context){
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();


        // try to read from JSON file
        // get information by using the tags
        // construct a com.example.sabrinapalmer.miniapp2.Recipe Object for each recipe in JSON
        // add the object to arraylist
        // return arraylist
        try{
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");

            // for loop to go through each recipe in your recipes array

            for (int i = 0; i < recipes.length(); i++){
                Recipe recipe = new Recipe();
                recipe.title = recipes.getJSONObject(i).getString("title");
                recipe.description = recipes.getJSONObject(i).getString("description");
                recipe.imageUrl = recipes.getJSONObject(i).getString("image");
                recipe.instructionUrl = recipes.getJSONObject(i).getString("url");
                recipe.label = recipes.getJSONObject(i).getString("dietLabel");
                recipe.servings = recipes.getJSONObject(i).getInt("servings");
                recipe.prepTime = recipes.getJSONObject(i).getString("prepTime");

                // add to arraylist
                recipeList.add(recipe);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }


    // helper method that loads from any Json file
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }



    protected Recipe(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        instructionUrl = in.readString();
        description = in.readString();
        label = in.readString();
        servings = in.readInt();
        prepTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(instructionUrl);
        dest.writeString(description);
        dest.writeString(label);
        dest.writeInt(servings);
        dest.writeString(prepTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}