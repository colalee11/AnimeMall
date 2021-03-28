package com.dlsd.property.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 疾病
 */
public class Disease implements Serializable {
    @SerializedName("diseaseId")
    private int id;
    @SerializedName("diseaseName")
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
