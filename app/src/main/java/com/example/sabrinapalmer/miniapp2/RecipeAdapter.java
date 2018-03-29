package com.example.sabrinapalmer.miniapp2;

/**
 * Created by sabrinapalmer on 3/18/18.
 */

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter{


    // adapter takes the app itself and a list of data to display
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflater;

    // constructor
    public RecipeAdapter(Context mContext, ArrayList<Recipe> mRecipeList){

        // initialize instances variables
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // methods
    // a list of methods we need to override

    // gives you the number of recipes in the data source
    @Override
    public int getCount(){
        return mRecipeList.size();
    }

    // returns the item at specific position in the data source

    @Override
    public Object getItem(int position){
        return mRecipeList.get(position);
    }

    // returns the row id associated with the specific position in the list
    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        // check if the view already exists
        // if yes, you don't need to inflate and findViewbyID again
        if (convertView == null){
            // inflate
            convertView = mInflater.inflate(R.layout.list_item_recipe, parent, false);
            // add the views to the holder
            holder = new ViewHolder();
            // views
            holder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_serving);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_thumbnail);
            holder.prepTimeTextView = convertView.findViewById(R.id.recipe_list_time);
            holder.makeNowButton = convertView.findViewById(R.id.cookNowButton);

            // add the holder to the view
            // for future use
            convertView.setTag(holder);
        }
        else{
            // get the view holder from convertview
            holder = (ViewHolder)convertView.getTag();
        }
        // get relative subview of the row view
        TextView titleTextView = holder.titleTextView;
        TextView servingTextView = holder.servingTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        TextView prepTimeTextView = holder.prepTimeTextView;
        Button makeNowButton = holder.makeNowButton;

        // get corresonpinding recipe for each row
        final Recipe recipe = (Recipe)getItem(position);


        // update the row view's textviews and imageview to display the information

        // titleTextView
        titleTextView.setText(recipe.title);
        titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        titleTextView.setTextSize(18);

        // servingTextView
        servingTextView.setText(recipe.servings + " servings");
        servingTextView.setTextSize(14);
        servingTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

        prepTimeTextView.setText(recipe.prepTime);
        prepTimeTextView.setTextSize(14);
        prepTimeTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));


        makeNowButton.setText(recipe.title);
        makeNowButton.setTextSize(10);

        final Intent openRecipe = new Intent(Intent.ACTION_VIEW);
        openRecipe.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        openRecipe.setData(Uri.parse(recipe.instructionUrl));
        final PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, openRecipe, 0);

        makeNowButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick (View view){
                final String biggyText = "Click here to pull up the instructions for " + recipe.title + "!!";
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "channel_ID")
                        .setSmallIcon(R.drawable.orange)
                        .setContentTitle("Instructions!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                bigText.bigText(biggyText);
                mBuilder.setStyle(bigText);
                NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);
                manager.notify((int) System.currentTimeMillis(), mBuilder.build());

            }
                });






       /* makeNowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent recipeIntent = new Intent(mContext, )
            }

        })*/

        // imageView
        // use Picasso library to load image from the image url
        Picasso.with(mContext).load(recipe.imageUrl).into(thumbnailImageView);
        return convertView;

    }

    // viewHolder
    // is used to customize what you want to put into the view
    // it depends on the layout design of your row
    // this will be a private static class you have to define
    private static class ViewHolder{
        public TextView titleTextView;
        public TextView servingTextView;
        public ImageView thumbnailImageView;
        public TextView prepTimeTextView;
        public Button makeNowButton;
    }


    // intent is used to pass information between activities
    // intent -> package
    // sender, receiver


}
