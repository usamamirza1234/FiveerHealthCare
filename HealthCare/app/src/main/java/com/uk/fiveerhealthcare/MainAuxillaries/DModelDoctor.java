package com.uk.fiveerhealthcare.MainAuxillaries;

import android.graphics.drawable.Drawable;

public class DModelDoctor {
    String name;
    String type;
    String desc;
    Drawable image;

    public DModelDoctor(String name, String type, String desc) {
        this.name = name;
        this.type = type;
        this.desc = desc;
    }

    public DModelDoctor(String name, String type, String desc, Drawable image) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
