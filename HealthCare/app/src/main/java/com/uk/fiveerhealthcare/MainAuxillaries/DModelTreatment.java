package com.uk.fiveerhealthcare.MainAuxillaries;

public class DModelTreatment {
    public DModelTreatment() {
    }

    public DModelTreatment(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public DModelTreatment(String name, String desc, String image, String bg) {
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.bg = bg;
    }

    String name;
    String desc;
    String image;
    String bg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }
}
