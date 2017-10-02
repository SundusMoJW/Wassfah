package com.twins.osama.wasfa.Classes;

/**
 * Created by Osama on 9/22/2017.
 */

public class Menu {
    private int Id;
    private String category;
    private String filePath;

    public Menu( int Id,String category, String filePath) {
        this.category = category;
        this.filePath = filePath;
        this.Id=Id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
