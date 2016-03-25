package com.example.petermartinez.tapecitry;

/**
 * Created by petermartinez on 3/25/16.
 */
public class Asset {
    boolean isText;
    String essay;
    int id;

    public Asset(boolean isText, String essay) {
        this.isText = isText;
        this.essay = essay;
    }

    public Asset(boolean isText, int id) {
        this.isText = isText;
        this.id = id;
    }

    public Asset(boolean isText, String essay, int id) {
        this.isText = isText;
        this.essay = essay;
        this.id = id;
    }

    public Asset(boolean isText) { //will make a random asset
        this.isText = isText;
        if(isText){
            this.essay = getIpsum();
        } else {
            this.id = R.drawable.cafelataza;
        }
    }

    private String getIpsum(){
        String ipsum = "";
        String[] source = {"Integer faucibus dolor in nibh cursus, ut eleifend lacus auctor.",
                "Duis vitae sapien eu orci mollis imperdiet.",
                "Maecenas est leo, tincidunt eget felis eget, fringilla fermentum lacus.",
                "Donec non scelerisque augue.", "Pellentesque rhoncus aliquam efficitur.",
                "Duis velit quam, cursus non augue vitae, blandit pulvinar purus.",
                "Nulla ultricies eget libero sit amet dignissim.", "Fusce ac velit odio.",
                "Curabitur pretium tortor vitae luctus tincidunt.",
                "Nam vulputate odio commodo, tincidunt neque vel, elementum felis.",
                "Vivamus lectus tellus, accumsan et neque ac, congue auctor massa.",
                "Aliquam eget risus non neque pharetra consequat non at nisl.",
                "Duis vel mattis sapien.",
                "Praesent at risus sollicitudin, iaculis elit id, luctus lorem.",
                "Duis gravida vel nisl sit amet dapibus."};
    int k = (int) (1 + 10 * Math.random());
        for(int i = 0; i < k; i++){
            ipsum = ipsum + source[(int) (13 * Math.random())];
        }
    return ipsum;
    }

    public boolean getIsText() {
        return isText;
    }

    public void setIsText(boolean isText) {
        this.isText = isText;
    }

    public String getEssay() {
        return essay;
    }

    public void setEssay(String essay) {
        this.essay = essay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
