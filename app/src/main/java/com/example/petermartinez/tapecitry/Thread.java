package com.example.petermartinez.tapecitry;


import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by petermartinez on 3/21/16.
 */
public class Thread {
    String title;
    float rating;
    int duration;
    long dateCreated;
    long dateModified;
    int views;
    float lat;
    float lon;
    int assetCheat;
    int assetCheatColor;
    int distToPoint;
    float bearingToPoint;
    int user;


    public Thread(String title, float rating, int duration, long dateCreated, long dateModified, int views, float lat, float lon, int assetCheat, int assetCheatColor, int user) {
        this.title = title;
        this.rating = rating;
        this.duration = duration;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.views = views;
        this.lat = lat;
        this.lon = lon;
        this.assetCheat = assetCheat;
        this.assetCheatColor = assetCheatColor;
        this.distToPoint = -1;
        this.bearingToPoint = -1f;
        this.user = user;
    }

    public Thread(String title) {
        this.title = title;
        int tempI = (int) (21 * Math.random());
        this.rating = tempI/4;
        this.duration = (int) (600 * Math.random());
        this.dateCreated = (long) (System.currentTimeMillis() - 864000000*Math.random());
        this.dateModified = dateCreated;
        this.views = (int) (5000*Math.random());
        this.lat = getRandomSFLat();
        this.lat = getRandomSFLon();
        this.assetCheat = getRandomAsset((int) (16 * Math.random()));
        this.assetCheatColor = getRandomAssetColor((int) (16 * Math.random()));
        this.distToPoint = -1;
        this.bearingToPoint = -1f;
        this.user = 7;
    }


    public Thread newRandomThread(String newTitle){
        if(newTitle.equals("")){
            newTitle = getRandomName();
        }
        Thread newThread = new Thread(newTitle);
        return newThread;
    }

    public String getRandomName(){
        String[] article = {"The", "El", "La", "Los", "Las", "The", "The", "The", "A", "A", "" , "" , "", "" ,"",""};
        String[] place = {"Mission", "Valencia", "Coit", "Gold Rush", "Google", "Market", "Alcatraz", "Marin", "Potrero", "", "", "","",""};
        String[] thing = {"Cafe", "Taqueria", "Building", "Bistro", "Memorial", "Festival", "Massacre", "Battle", "Concert", "Tower", "Company", "","","","","",""};
        String name = "";
        int temp = (int) (Math.random()*article.length);
        name = name + article[temp];
        temp = (int) (Math.random()*place.length);
        name = name + " " + place[temp];
        temp = (int) (Math.random()*thing.length);
        name = name + " " + thing[temp];
        if(name.equals("  ")){
            return "Cafe La Taza";
        }
        return name;
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
                return (R.drawable.stump);
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
                return (R.color.button_trans);
        }
    }


    public float getRandomSFLat(){
        return ((float) (37.734663 + .070040 * Math.random()));
    }

    public float getRandomSFLon(){
        return ((float) (-122.450963 + .060157 * Math.random()));
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

    public int getAssetCheat() {
        return assetCheat;
    }

    public void setAssetCheat(int assetCheat) {
        this.assetCheat = assetCheat;
    }

    public int getAssetCheatColor() {
        return assetCheatColor;
    }

    public void setAssetCheatColor(int assetCheatColor) {
        this.assetCheatColor = assetCheatColor;
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

    public void setBearingToPoint(float bearingToPoint) {
        this.bearingToPoint = bearingToPoint;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}


