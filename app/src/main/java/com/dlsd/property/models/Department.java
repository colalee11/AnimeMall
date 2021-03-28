package com.dlsd.property.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 科室
 */
public class Department implements Serializable {

    @SerializedName("deptId")
    private int id;
    @SerializedName("deptName")
    private String name;

    private ArrayList<Disease> diseaseList;

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

    public ArrayList<Disease> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(ArrayList<Disease> diseaseList) {
        this.diseaseList = diseaseList;
    }
}
