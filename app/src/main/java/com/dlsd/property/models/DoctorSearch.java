package com.dlsd.property.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 在线问诊-筛选条件查询
 */
public class DoctorSearch implements Serializable {
    private ArrayList<Consultation> consultationList;
    private ArrayList<Department> departmentList;
    private ArrayList<Experience> experienceList;

    public ArrayList<Consultation> getConsultationList() {
        return consultationList;
    }

    public void setConsultationList(ArrayList<Consultation> consultationList) {
        this.consultationList = consultationList;
    }

    public ArrayList<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(ArrayList<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public ArrayList<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(ArrayList<Experience> experienceList) {
        this.experienceList = experienceList;
    }
}
