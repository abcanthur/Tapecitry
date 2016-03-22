//package com.example.petermartinez.tapecitry;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.media.Image;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CursorAdapter;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//
///**
// * Created by petermartinez on 3/21/16.
// */
//public class ThreadCursorAdapter extends CursorAdapter {
//    private LayoutInflater cursorInflater;
//
//    public ThreadCursorAdapter(Context context, Cursor c, int flags) {
//        super(context, c, flags);
//        cursorInflater = (LayoutInflater) context.getSystemService(
//                Context.LAYOUT_INFLATER_SERVICE
//        );
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        ImageView assetThumb = (ImageView) view.findViewById(R.id.asset_thumbnail);
//        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
//        TextView duration = (TextView) view.findViewById(R.id.duration);
//
//        TextView threadTitle = (TextView) view. findViewById(R.id.thread_title);
//        TextView threadAge = (TextView) view.findViewById(R.id.thread_age);
//        TextView threadViews = (TextView) view.findViewById(R.id.thread_views);
//
//        TextView distance = (TextView) view.findViewById(R.id.distance);
//        TextView compass = (TextView) view.findViewById(R.id.compass);
//
//
//        assetThumb.setImageResource(Integer.parseInt(cursor.getString(
//                        cursor.getColumnIndex(ThreadsSQLiteHelper.COL_ASSET_TYPES))));
//        assetThumb.setBackgroundColor(Integer.parseInt(cursor.getString(
//                cursor.getColumnIndex(ThreadsSQLiteHelper.COL_ASSET_COUNT))));
//        ratingBar.setRating(Integer.parseInt(cursor.getString(
//                cursor.getColumnIndex(ThreadsSQLiteHelper.COL_RATING))));
//        duration.setText(cursor.getString(
//                cursor.getColumnIndex(ThreadsSQLiteHelper.COL_DUR)));
//
//        threadTitle.setText(cursor.getString(
//                cursor.getColumnIndex(ThreadsSQLiteHelper.COL_THR_TITLE)));
//        threadAge.setText(cursor.getString(
//                cursor.getColumnIndex(ThreadsSQLiteHelper.COL_DATE_CRE)));
//        threadViews.setText(cursor.getString(
//                cursor.getColumnIndex(ThreadsSQLiteHelper.COL_VIEWS)));
//
//
//
//
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return cursorInflater.inflate(R.layout.results_list, parent, false);
//    }
//}
