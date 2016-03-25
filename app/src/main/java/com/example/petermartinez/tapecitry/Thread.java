package com.example.petermartinez.tapecitry;


import android.annotation.TargetApi;
import android.os.Parcelable;
import android.util.Log;

import java.util.Comparator;

/**
 * Created by petermartinez on 3/21/16.
 */
@TargetApi(19)
public class Thread {
    int id;
    int user;
    String title;
    int knot;
    int knotCount;
    int node;
    long dateCreated;
    long dateModified;
    int assetType;
    int assetCount;
    float lat;
    float lon;
    int duration;
    float rating;
    int ratingCount;
    int length;
    int views;
    int distToPoint;
    int bearingToPoint;


    public Thread(int id, int user, String title, int knot, int knotCount, int node, long dateCreated, long dateModified, int assetType, int assetCount, float lat, float lon, int duration, float rating, int ratingCount, int length, int views) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.knot = knot;
        this.knotCount = knotCount;
        this.node = node;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.assetType = assetType;
        this.assetCount = assetCount;
        this.lat = lat;
        this.lon = lon;
        this.duration = duration;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.length = length;
        this.views = views;
    }

    public Thread(String title) {
        this.id = -1;
        this.title = title;
        int tempI = (int) (13 * Math.random());
        this.rating = tempI/4 + 2;
        this.duration = (int) (600 * Math.random());
        this.dateCreated = (long) (System.currentTimeMillis() - 864000000*Math.random());
        this.dateModified = dateCreated;
        this.views = (int) (5000*Math.random());
        this.lat = getRandomSFLat();
        this.lon = getRandomSFLon();
        this.assetType = getRandomAsset((int) (16 * Math.random()));
        this.assetCount = getRandomAssetColor((int) (16 * Math.random()));
        this.distToPoint = -1;
        this.bearingToPoint = -1;
        this.user = 7;
    }

    public static int haversineTwoPoints(float lat1, float lon1, float lat2, float lon2){
        int R = 6378100;
            float latDistance = toRad(lat2-lat1);
            float lonDistance = toRad(lon2-lon1);
            float a = (float) (Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                    Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                            Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));
            float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
            return (int) (R * c);
        }

        private static float toRad(float value) {
            return (float) (value * Math.PI / 180);
        }

        public static int bearing(float lat1, float lon1, float lat2, float lon2){
            double longitude1 = lon1;
            double longitude2 = lon2;
            double latitude1 = Math.toRadians(lat1);
            double latitude2 = Math.toRadians(lat2);
            double longDiff= Math.toRadians(longitude2-longitude1);
            double y= Math.sin(longDiff)*Math.cos(latitude2);
            double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
            double resultDegree= (Math.toDegrees(Math.atan2(y, x))+360)%360;
            Log.i("THREAD", "bearingDegree " + resultDegree);
            return (int) resultDegree;
        }



    public static Thread newRandomThread(String newTitle){
        if(newTitle.equals("")){
            newTitle = getRandomName();
        }
        Thread newThread = new Thread(newTitle);
        return newThread;
    }

    public static String getRandomName(){
        String[] article = {"The ", "El ", "La ", "Los ", "Las ", "The ", "The ", "The ", "A ", "A ", "" , "" , "", ""};
        String[] place = {"Mission ", "Valencia ", "Coit ", "Gold Rush ", "Google ", "Market ", "Alcatraz ", "Marin ", "Potrero ", "", "", "",""};
        String[] thing = {"Cafe", "Taqueria", "Building", "Bistro", "Memorial", "Festival", "Massacre", "Battle", "Concert", "Tower", "Company", "","","",""};
        String name = "";
        int temp = (int) (Math.random()*article.length);
        name = name + article[temp];
        temp = (int) (Math.random()*place.length);
        name = name + place[temp];
        temp = (int) (Math.random()*thing.length);
        name = name + thing[temp];
        if(name.isEmpty()){
            return "Cafe La Taza";
        }
        return name;
    }

    public int getRandomAsset2(int asset) {
        switch (asset) {
            case 0:
                return (android.R.drawable.ic_menu_search);
            case 1:
                return (android.R.drawable.sym_def_app_icon);
            case 2:
                return (android.R.drawable.ic_dialog_map);
            case 3:
                return (android.R.drawable.ic_menu_compass);
            case 4:
                return (android.R.drawable.ic_lock_idle_low_battery);
            case 5:
                return (android.R.drawable.presence_video_away);
            case 6:
                return (android.R.drawable.presence_audio_online);
            case 7:
                return (android.R.drawable.sym_call_missed);
            case 8:
                return (android.R.drawable.ic_menu_always_landscape_portrait);
            case 9:
                return (android.R.drawable.ic_menu_report_image);
            case 10:
                return (android.R.drawable.ic_menu_close_clear_cancel);
            case 11:
                return (android.R.drawable.ic_menu_crop);
            case 12:
                return (android.R.drawable.ic_menu_gallery);
            case 13:
                return (android.R.drawable.ic_menu_upload_you_tube);
            case 14:
                return (android.R.drawable.ic_menu_week);
            case 15:
                return (android.R.drawable.btn_radio);
            default:
                return (android.R.drawable.btn_dropdown);
        }
    }

    public int getRandomAsset(int asset) {
        switch (asset) {
            case 0:
                return (R.drawable.alamosquare);
            case 1:
                return (R.drawable.coit);
            case 2:
                return (R.drawable.ferrybldg);
            case 3:
                return (R.drawable.foodtruck);
            case 4:
                return (R.drawable.goldengate);
            case 5:
                return (R.drawable.homeless);
            case 6:
                return (R.drawable.marketst);
            case 7:
                return (R.drawable.mine1);
            case 8:
                return (R.drawable.mine2);
            case 9:
                return (R.drawable.mine3);
            case 10:
                return (R.drawable.mine4);
            case 11:
                return (R.drawable.poposb50);
            case 12:
                return (R.drawable.prideparade);
            case 13:
                return (R.drawable.starbucks);
            case 14:
                return (R.drawable.cafelataza);
            case 15:
                return (R.drawable.transamerica);
            default:
                return (R.drawable.bucks);
        }
    }

    public int getRandomAssetColor(int asset) {
        switch (asset) {
            case 0:
                return (R.color.Color_0);
            case 1:
                return (R.color.Color_1);
            case 2:
                return (R.color.Color_2);
            case 3:
                return (R.color.Color_3);
            case 4:
                return (R.color.Color_4);
            case 5:
                return (R.color.Color_5);
            case 6:
                return (R.color.Color_6);
            case 7:
                return (R.color.Color_7);
            case 8:
                return (R.color.Color_8);
            case 9:
                return (R.color.Color_9);
            case 10:
                return (R.color.Color_10);
            case 11:
                return (R.color.Color_11);
            case 12:
                return (R.color.Color_12);
            case 13:
                return (R.color.Color_13);
            case 14:
                return (R.color.Color_14);
            case 15:
                return (R.color.Color_15);
            default:
                return (R.color.colorAccent);
        }
    }


    public float getRandomSFLat(){
        float i = ((float) (37.734663 + .070040 * Math.random()));
        Log.i("THREAD", "random Lat = " + i);
        return i;
//        return ((float) (37.734663 + .070040 * Math.random()));

    }

    public float getRandomSFLon(){
        float i = ((float) ((.060157 * Math.random()) - 122.450963));
        Log.i("THREAD", "random Log = " + i);
        return i;
//        return ((float) ((.060157 * Math.random()) - 122.450963));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public int getAssetType() {
        return assetType;
    }

    public void setAssetType(int assetType) {
        this.assetType = assetType;
    }

    public int getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(int assetCount) {
        this.assetCount = assetCount;
    }

    public int getDistToPoint() {
        return distToPoint;
    }

    public void setDistToPoint(int distToPoint) {
        this.distToPoint = distToPoint;
    }

    public float getBearingToPoint() {
        return bearingToPoint;
    }

    public void setBearingToPoint(int bearingToPoint) {
        this.bearingToPoint = bearingToPoint;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getKnot() {
        return knot;
    }

    public void setKnot(int knot) {
        this.knot = knot;
    }

    public int getKnotCount() {
        return knotCount;
    }

    public void setKnotCount(int knotCount) {
        this.knotCount = knotCount;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public static Comparator<Thread> titleComparator = new Comparator<Thread>() {
        public int compare(Thread t1, Thread t2) {
//            String title1 = t1.getTitle().toUpperCase();
//            String title2 = t2.getTitle().toUpperCase();
//
//            //ascending order
            return t1.getTitle().toUpperCase().compareTo(t2.getTitle().toUpperCase());
        }};

    public static Comparator<Thread> ratingComparator = new Comparator<Thread>() {
        public int compare(Thread t1, Thread t2) {
            return Float.compare(t2.getRating(), t1.getRating());
        }};

    public static Comparator<Thread> durationComparator = new Comparator<Thread>() {
        public int compare(Thread t1, Thread t2) {
            return t1.getDuration() - t2.getDuration();
        }};

    public static Comparator<Thread> distanceComparator = new Comparator<Thread>() {
        public int compare(Thread t1, Thread t2) {
            return t1.getDistToPoint() - t2.getDistToPoint();
        }};


    public static Comparator<Thread> ageComparator = new Comparator<Thread>() {
        public int compare(Thread t1, Thread t2) {
            return Long.compare(t1.getDateCreated(), t2.getDateCreated());
        }};

    public static Comparator<Thread> viewsComparator = new Comparator<Thread>() {
        public int compare(Thread t1, Thread t2) {
            return t2.getViews() - t1.getViews();
        }};

}


