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

import com.squareup.picasso.Picasso;

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
        ImageView compass = (ImageView) view.findViewById(R.id.compass);

        Picasso.with(getContext())
                .load(thread.getAssetType())
                .placeholder(android.R.drawable.ic_dialog_alert)
                .resize(100, 100) //wish I could do this dynamically, w,h of assetThumb
                .centerCrop()
                .into(assetThumb);

        ratingBar.setRating(thread.getRating());
        duration.setText(secondsToColons(thread.getDuration()));

        threadTitle.setText(thread.getTitle());
        threadAge.setText(formatAge(thread.getDateCreated()));
        threadViews.setText(formatViews(thread.getViews()));

        distance.setText(formatDistance(thread.getDistToPoint()));
//        compass.setText(getBearing(Math.round(thread.getBearingToPoint())));
        Picasso.with(getContext())
                .load(R.drawable.bearing_arrow)
                .placeholder(R.drawable.bearing_arrow)
                .rotate(thread.getBearingToPoint())
                .into(compass);

        return view;
    }

    private String formatAge(long date){
        long now = System.currentTimeMillis();
        long age = now - date;
        long days = age/86400000; //millis in a day
        if(days > 365){
            return "created " + days/365 + " years ago";
        } else if (days > 70){
            return "created " + days/30 + " months ago";
        } else if (days > 13){
            return "created " + days/7 + " weeks ago";
        } else  if (days > 1){
            return "created " + days + " days ago";
        } else if (age > 5400000){
            return "created " + age/3600000 + " hours ago";
        } else {
            return "created " + age/600000 + " minutes ago";
        }
    }

    private String formatViews(int views){
        String viewsPrefix = String.valueOf(views);
        String viewsSuffix = " views";
        if(views > 1000){
            viewsPrefix = String.valueOf((Math.round(views/100))/10);
            viewsSuffix = "k"+viewsSuffix;
        }
        return viewsPrefix + viewsSuffix;
    }

    public static String formatDistance(int meters){
        String string = String.valueOf(Math.round((meters + 161) * 0.000621371)); //+161 ensures it's at least a tenth of a mile, very hacky
        String string2 = String.valueOf(Math.round((meters + 161) * 0.00621371));//actually makes it ten times longer
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
