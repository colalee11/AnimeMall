package com.dlsd.property.models;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private String title;
    private String iconId;
    private String id;

    public MenuItem(String id, String title, String iconId) {
        this.id = id;
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
