package com.dlsd.property.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 咨询类型
 */
public class Consultation implements Serializable {

    @SerializedName("diagnosisModeId")
    private int id;
    @SerializedName("diagnosisModeName")
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
