package com.dlsd.property.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 经验
 */
public class Experience implements Serializable {

    @SerializedName("experienceId")
    private int id;
    @SerializedName("experienceName")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
