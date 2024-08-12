package com.signsy.signsymain;

public class fItem {
    String Star,description;

    public fItem() {}

    public fItem(String star, String description) {
        Star = star;
        this.description = description;
    }

    public String getStar() {
        return Star;
    }

    public void setStar(String star) {
        Star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
