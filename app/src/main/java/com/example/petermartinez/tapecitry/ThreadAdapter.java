package com.example.petermartinez.tapecitry;

import android.content.Context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Thread thread = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.results_list, parent, false);
        }

        ImageView assetThumb = (ImageView) view.findViewById(R.id.asset_thumbnail);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        TextView duration = (TextView) view.findViewById(R.id.duration);

        TextView threadTitle = (TextView) view.findViewById(R.id.thread_title);
        TextView threadAge = (TextView) view.findViewById(R.id.thread_age);
        TextView threadViews = (TextView) view.findViewById(R.id.thread_views);

        TextView distance = (TextView) view.findViewById(R.id.distance);
        TextView compass = (TextView) view.findViewById(R.id.compass);

        assetThumb.setImageResource(thread.getAssetCheat());
        assetThumb.setBackgroundColor(thread.getAssetCheatColor());
        ratingBar.setRating(thread.getRating());
        duration.setText(thread.getDuration());

        threadTitle.setText(thread.getTitle());
        threadAge.setText((int) thread.getDateCreated());
        threadViews.setText(thread.getViews());

        distance.setText(thread.getDistToPoint());
        compass.setText(Float.toString(thread.getBearingToPoint()));

        return convertView;
    }

}
