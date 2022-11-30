package com.example.swe;

public class firebasemdel {
    String title;
    String cntent;
    public firebasemdel()
    {

    }

    public firebasemdel(String title, String cntent) {
        this.title = title;
        this.cntent = cntent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCntent() {
        return cntent;
    }

    public void setCntent(String cntent) {
        this.cntent = cntent;
    }
}
