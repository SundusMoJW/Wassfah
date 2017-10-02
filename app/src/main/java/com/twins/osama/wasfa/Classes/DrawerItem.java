package com.twins.osama.wasfa.Classes;

/**
 * Created by Amal on 1/1/2016.
 */
public class DrawerItem {
    private  String title;
    private  int image;
    private int image_on;
    private boolean isSelected;

    public DrawerItem(String title, int image, boolean isSelected) {
        this.title = title;
        this.image = image;
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
