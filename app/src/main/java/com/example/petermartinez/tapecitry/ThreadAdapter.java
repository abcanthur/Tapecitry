package com.example.petermartinez.tapecitry;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by petermartinez on 3/22/16.
 */
public class ThreadAdapter extends ArrayAdapter<Thread> {
    public ThreadAdapter(Context context, ArrayList<Thread> threads) {
        super(context, 0, threads);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Thread thread = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.results_list, parent, false);
        }

        ImageView assetThumb = (ImageView) view.findViewById(R.id.asset_thumbnail);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        TextView duration = (TextView) view.findViewById(R.id.duration);

        TextView threadTitle = (TextView) view.findViewById(R.id.thread_title);
        TextView threadAge = (TextView) view.findViewById(R.id.thread_age);
        TextView threadViews = (TextView) view.findViewById(R.id.thread_views);

        TextView distance = (TextView) view.findViewById(R.id.distance);
        TextView compass = (TextView) view.findViewById(R.id.compass);

        ColorFilter cf = new PorterDuffColorFilter(thread.getAssetCount(), PorterDuff.Mode.MULTIPLY);

        assetThumb.setImageResource(thread.getAssetType());
//        assetThumb.setColorFilter(cf);
//        assetThumb.setBackgroundColor(thread.getAssetCount());
//        assetThumb.setImageAlpha(150);
//        assetThumb.setColorFilter(thread.getAssetCount(), PorterDuff.Mode.LIGHTEN);
//        assetThumb.setBackgroundColor(thread.getAssetCount());
        ratingBar.setRating(thread.getRating());
        duration.setText(secondsToColons(thread.getDuration()));

        threadTitle.setText(thread.getTitle());
        threadAge.setText("created " + String.valueOf(thread.getDateCreated()/86400000 - 16873) + " days ago");
        threadViews.setText(String.valueOf(thread.getViews()) + " views");

        distance.setText(formatDistance(thread.getDistToPoint()));
        compass.setText(getBearing(Math.round(thread.getBearingToPoint())));

        return view;
    }

    private String formatDistance(int meters){
        String string = String.valueOf(Math.round(meters * 0.000621371)+1);
        String string2 = String.valueOf(Math.round(meters * 0.00621371)+10);//actually makes it ten times longer
        string = string + "." + string2.charAt(string2.length()-1);
        return string;
    }

    private String getBearing(int bearing){
        String coordNames[] = {"N","NNE", "NE","ENE","E", "ESE","SE","SSE", "S","SSW", "SW","WSW", "W","WNW", "NW","NNW", "N"};
        double directionid = Math.round(bearing / 22.5);
        // no of array contain 360/16=22.5
        if (directionid < 0) {
            directionid = directionid + 16;
            //no. of contains in array
        }
        return coordNames[(int) directionid];
//        return String.valueOf(directionid);
    }

    private String secondsToColons(int dur){
        int sec = dur%60;
        dur = dur - sec;
        int min = (int) (dur/60);
        String colons = String.valueOf(min) + ":";
        if(sec < 10){ colons = colons + "0";}
        colons = colons + String.valueOf(sec);
        return colons;
    }

}
