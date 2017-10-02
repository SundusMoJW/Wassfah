package com.twins.osama.wasfa.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by Osama on 9/23/2017.
 */

public class Recipe extends RealmObject implements Parcelable {
    private String titel;
    private String imgPath;
    private String durtaion;
    private int cid;
    private int wid;
    private boolean isSeleact;
    private String description;
    private String steps;

    public Recipe() {
    }

    public Recipe(int cid, int wid,String titel, String imgPath, String durtaion, String description, String steps, boolean isSeleact) {
        this.titel = titel;
        this.imgPath = imgPath;
        this.durtaion = durtaion;
        this.cid = cid;
        this.description = description;
        this.steps = steps;
        this.isSeleact = isSeleact;
        this.wid=wid;
    }

    protected Recipe(Parcel in) {
        titel = in.readString();
        imgPath = in.readString();
        durtaion = in.readString();
        cid = in.readInt();
        wid = in.readInt();
        isSeleact = in.readByte() != 0;
        description = in.readString();
        steps = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public boolean isSeleact() {
        return isSeleact;
    }

    public void setSeleact(boolean seleact) {
        isSeleact = seleact;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDurtaion() {
        return durtaion;
    }

    public void setDurtaion(String durtaion) {
        this.durtaion = durtaion;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titel);
        parcel.writeString(imgPath);
        parcel.writeString(durtaion);
        parcel.writeInt(cid);
        parcel.writeInt(wid);
        parcel.writeByte((byte) (isSeleact ? 1 : 0));
        parcel.writeString(description);
        parcel.writeString(steps);
    }
}
